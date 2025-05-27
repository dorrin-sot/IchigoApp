package com.example.jikanapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.room.Room
import com.example.jikanapp.service.AppDatabase
import com.example.jikanapp.service.Repository
import com.example.jikanapp.service.RetrofitHelper
import com.example.jikanapp.ui.theme.AppTheme
import com.example.jikanapp.viewmodel.FruitsListViewModel
import com.example.jikanapp.view.CustomSearchbar
import com.example.jikanapp.view.FilterChip
import com.example.jikanapp.view.fruitslist.FruitsListView
import com.example.jikanapp.view.LoadingRefreshButton


class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    val db = Room
      .databaseBuilder(applicationContext, AppDatabase::class.java, "jikan-db")
      .fallbackToDestructiveMigration(dropAllTables = true)
      .build()
    val viewModel = FruitsListViewModel(Repository(db, RetrofitHelper.fruitApi))

    setContent {
      AppTheme {
        Scaffold(
          modifier = Modifier.fillMaxSize(),
          topBar = {
            val query by rememberSaveable { viewModel.query }

            TopAppBar(
              title = {
                AnimatedVisibility(
                  visible = query != null,
                  enter = fadeIn() + slideInVertically(),
                  exit = fadeOut() + slideOutVertically()
                ) {
                  CustomSearchbar(
                    value = query,
                    onValueChange = { viewModel.search(it) }
                  )
                }
                if (query == null)
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
          FruitsListView(
            viewModel = viewModel,
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}
