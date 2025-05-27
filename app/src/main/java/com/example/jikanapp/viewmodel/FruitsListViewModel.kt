package com.example.jikanapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jikanapp.model.AncestryLevel
import com.example.jikanapp.service.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FruitsListViewModel(private val repository: Repository) : ViewModel() {
  val dataStatus get() = repository.databaseStatus
  val fruits get() = repository.fruits
  val filter get() = repository.filter
  val query get() = repository.query

  var searchJob: Job? = null

  fun fetchFruits() = viewModelScope.launch { repository.fetchFruits() }

  fun filterByAncestor(key: AncestryLevel, value: String) =
    viewModelScope.launch { repository.filterByAncestor(key, value) }

  fun removeFilter() = viewModelScope.launch { repository.removeFilter() }

  fun search(query: String?) {
    searchJob?.cancel()
    searchJob = viewModelScope.launch {
//      delay(5000)
      println("Searching for '$query'")
      repository.search(query)
    }
    searchJob?.start()
  }
}
