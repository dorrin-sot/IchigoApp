package com.example.jikanapp.views

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jikanapp.services.DataStatus

@Composable
fun LoadingRefreshButton(
  dataStatus: DataStatus,
  onRefresh: () -> Unit
) {
  IconButton(
    onClick = onRefresh,
    enabled = dataStatus == DataStatus.DatabaseUpdated,
    modifier = Modifier.size(20.dp),
  ) {
    when (dataStatus) {
      DataStatus.DatabaseUpdated ->
        Icon(Icons.Default.Refresh, "Refresh List")

      DataStatus.DatabaseUpdating ->
        CircularProgressIndicator()
    }
  }
}
