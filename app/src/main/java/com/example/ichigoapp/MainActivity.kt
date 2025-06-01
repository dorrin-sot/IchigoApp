package com.example.ichigoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.ichigoapp.ui.theme.IchigoTheme
import com.example.ichigoapp.view.CustomSearchbar
import com.example.ichigoapp.view.FilterChip
import com.example.ichigoapp.view.LoadingRefreshButton
import com.example.ichigoapp.view.fruitslist.FruitsListView
import com.example.ichigoapp.viewmodel.FruitsListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    val viewModel by viewModels<FruitsListViewModel>()

    setContent {
      IchigoTheme {
        val query by rememberSaveable { viewModel.query }

        Scaffold(
          modifier = Modifier.fillMaxSize(),
          topBar = {
            TopAppBar(
              title = {
                Text(
                  "Fruits",
                  fontWeight = FontWeight.Black,
                  style = MaterialTheme.typography.titleLarge,
                )
              },
              actions = {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.End,
                  modifier = Modifier
                    .padding(start = 10.dp, end = 7.5.dp)
                ) {

                  if (query == null) {
                    IconButton(onClick = { viewModel.search("") }) {
                      Icon(Icons.Default.Search, "Search")
                    }
                  } else {
                    IconButton(onClick = { viewModel.search(null) }) {
                      Icon(Icons.Default.SearchOff, "DismissSearch")
                    }
                  }

                  val filter by rememberSaveable { viewModel.filter }
                  if (filter != null) {
                    FilterChip(
                      label = "${filter!!.first}: ${filter!!.second}".capitalize(),
                      onDismiss = { viewModel.removeFilter() }
                    )
                  }


                  val dataStatus by rememberSaveable { viewModel.dataStatus }
                  LoadingRefreshButton(
                    dataStatus,
                    onRefresh = { viewModel.fetchFruits() }
                  )
                }
              },
              modifier = Modifier.zIndex(15f),
              expandedHeight =
                if (query == null) TopAppBarDefaults.TopAppBarExpandedHeight
                else 60.dp
            )
          }
        ) { innerPadding ->
          Box {
            FruitsListView(
              modifier = Modifier
                .padding(innerPadding)
                .padding(
                  top =
                    if (query == null) 0.dp
                    else OutlinedTextFieldDefaults.MinHeight + 5.dp
                )
            )

            AnimatedVisibility(
              visible = query != null,
              enter = fadeIn() + slideInVertically(),
              exit = fadeOut() + slideOutVertically(),
              modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(innerPadding)
            ) {
              CustomSearchbar(
                value = query,
                onValueChange = { viewModel.search(it) }
              )
            }
          }
        }
      }
    }
  }
}
