package com.example.jikanapp.service

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.jikanapp.model.Fruit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
  val db: AppDatabase,
  val fruitApi: FruitApi,
) {
  var dataStatus = mutableStateOf(DataStatus.Default)
  var fruits = mutableStateListOf<Fruit>()
  var filter = mutableStateOf<Pair<String, String>?>(null)
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
    dataStatus.value = DataStatus.DatabaseUpdating
    val apiResults = apiFetchFruits()
    if (apiResults.isNotEmpty()) {
      updateDbWithResult(apiResults)

      updateFruitsFromDb()
      dataStatus.value = DataStatus.DatabaseUpdated
    }
  }

  suspend fun filterByAncestor(key: String, value: String) {
    assert(arrayOf("family", "order", "genus").contains(key))
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

enum class DataStatus {
  DatabaseUpdating, DatabaseUpdated;

  companion object {
    val Default = DatabaseUpdating
  }
}
