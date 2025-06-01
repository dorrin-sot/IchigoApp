package com.example.ichigoapp.view

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ichigoapp.service.DatabaseStatus

@Composable
fun LoadingRefreshButton(
  databaseStatus: DatabaseStatus,
  onRefresh: () -> Unit
) {
  IconButton(
    onClick = onRefresh,
    enabled = databaseStatus == DatabaseStatus.Updated,
    modifier = Modifier.size(20.dp),
  ) {
    when (databaseStatus) {
      DatabaseStatus.Updated ->
        Icon(Icons.Default.Refresh, "Refresh List")

      DatabaseStatus.Updating ->
        CircularProgressIndicator()
    }
  }
}
