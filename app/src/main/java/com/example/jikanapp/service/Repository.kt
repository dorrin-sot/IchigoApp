package com.example.jikanapp.service

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.jikanapp.model.AncestryLevel
import com.example.jikanapp.model.Fruit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
  val db: AppDatabase,
  val fruitApi: FruitApi,
) {
  var databaseStatus = mutableStateOf(DatabaseStatus.Default)
  var fruits = mutableStateListOf<Fruit>()
  var filter = mutableStateOf<Pair<AncestryLevel, String>?>(null)
  var query = mutableStateOf<String?>(null)

  private var _fruits: List<Fruit>
    get() = fruits
    set(value) {
      fruits.removeAll { true }
      fruits.addAll(value)
    }

  private suspend fun updateFruitsFromDb() {
    val dao = db.fruitDao()
    val result = withContext(Dispatchers.IO) {
      dao.get(filter.value, query.value?.ifEmpty { null })
    }
    _fruits = result
  }

  private suspend fun updateDbWithResult(result: List<Fruit>) {
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
    fetchFruits()
  }

  suspend fun apiFetchFruits(): List<Fruit> =
    withContext(Dispatchers.IO) {
      fruitApi.fetchAll()
        .runCatching { execute() }
        .getOrNull()
        ?.body()
        ?: emptyList()
    }
}
