package com.example.ichigoapp.view.fruitslist

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ichigoapp.viewmodel.FruitsListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FruitsListView(modifier: Modifier = Modifier) {
  val viewModel = hiltViewModel<FruitsListViewModel>()

  val fruits = viewModel.fruits
  LaunchedEffect(Unit) {
    viewModel.fetchFruits()
  }

  LazyColumn(
    modifier = Modifier
      .pullToRefresh(
        isRefreshing = false,
        state = rememberPullToRefreshState(),
        onRefresh = { viewModel.fetchFruits() }
      )
      .padding(5.dp)
      .then(modifier),
  ) {
    fruits
      .groupBy { it.name.uppercase().trim().first() }
      .toSortedMap()
      .forEach { initial, list ->
        stickyHeader(initial) { FruitsListHeaderLetterView(initial) }

        items(list) { fruit -> FruitsListItemView(fruit) }
      }
  }
}
