package com.example.ichigoapp.service

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.ichigoapp.model.AncestryLevel
import com.example.ichigoapp.model.Fruit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(
  val db: AppDatabase,
  val fruitApi: FruitApi,
) {
  var databaseStatus = mutableStateOf(DatabaseStatus.Default)
  var fruits = mutableStateListOf<Fruit>()
  var filter = mutableStateOf<Pair<AncestryLevel, String>?>(null)
  var query = mutableStateOf<String?>(null)

  @VisibleForTesting
  var _fruits: List<Fruit>
    get() = fruits
    set(value) {
      fruits.removeAll { true }
      fruits.addAll(value)
    }

  @VisibleForTesting
  suspend fun updateFruitsFromDb() {
    val dao = db.fruitDao()
    val result = withContext(Dispatchers.IO) {
      dao.get(filter.value, query.value?.ifEmpty { null })
    }
    _fruits = result
  }

  @VisibleForTesting
  suspend fun updateDbWithResult(result: List<Fruit>) {
    val dao = db.fruitDao()

    withContext(Dispatchers.IO) {
      dao.deleteAll()
      dao.insertAll(*result.toTypedArray())
    }
  }

  suspend fun fetchFruits() {
    updateFruitsFromDb()
    databaseStatus.value = DatabaseStatus.Updating
    val apiResults = apiFetchFruits()
    if (apiResults.isNotEmpty()) {
      updateDbWithResult(apiResults)

      updateFruitsFromDb()
      databaseStatus.value = DatabaseStatus.Updated
    }
  }

  suspend fun filterByAncestor(key: AncestryLevel, value: String) {
    filter.value = key to value
    fetchFruits()
  }

  suspend fun removeFilter() {
    filter.value = null
    fetchFruits()
  }

  suspend fun search(query: String?) {
    this.query.value = query
    delay(1000) // Debounce :)
    fetchFruits()
  }

  @VisibleForTesting
  suspend fun apiFetchFruits(): List<Fruit> =
    withContext(Dispatchers.IO) {
      fruitApi.fetchAll()
        .runCatching { execute() }
        .getOrNull()
        ?.body()
        ?: emptyList()
    }
}
