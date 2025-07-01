package com.example.ichigoapp.view.fruitslist

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ichigoapp.model.Fruit
import com.example.ichigoapp.view.fruitslist.FruitsListViewTestTags.FRUITS_LIST_TEST_TAG
import com.example.ichigoapp.view.fruitslist.FruitsListViewTestTags.fruitItemTestTag
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
      .testTag(FRUITS_LIST_TEST_TAG)
      .then(modifier),
  ) {
    fruits
      .groupBy { it.name.uppercase().trim().first() }
      .toSortedMap()
      .forEach { initial, list ->
        stickyHeader(initial) {
          FruitsListHeaderLetterView(initial)
        }

        items(list) { fruit ->
          FruitsListItemView(
            fruit,
            Modifier.testTag(fruitItemTestTag(fruit))
          )
        }
      }
  }
}

@VisibleForTesting
object FruitsListViewTestTags {
  const val FRUITS_LIST_TEST_TAG = "fruits-list"

  fun fruitItemTestTag(fruit: Fruit): String = "${fruit.id}-item"
}