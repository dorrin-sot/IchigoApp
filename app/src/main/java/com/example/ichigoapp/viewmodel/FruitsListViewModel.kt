package com.example.ichigoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ichigoapp.model.AncestryLevel
import com.example.ichigoapp.service.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FruitsListViewModel @Inject constructor(
  private val repository: Repository
) : ViewModel() {
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
      println("Searching for '$query'")
      repository.search(query)
    }
    searchJob?.start()
  }
}
