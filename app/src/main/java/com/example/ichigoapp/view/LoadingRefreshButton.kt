package com.example.ichigoapp.view

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.ichigoapp.service.DatabaseStatus
import com.example.ichigoapp.view.LoadingRefreshButtonTestTags.UPDATED_TEST_TAG
import com.example.ichigoapp.view.LoadingRefreshButtonTestTags.UPDATING_TEST_TAG

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
        Icon(
          Icons.Default.Refresh, "Refresh List",
          modifier = Modifier.testTag(UPDATED_TEST_TAG)
        )

      DatabaseStatus.Updating ->
        CircularProgressIndicator(
          modifier = Modifier.testTag(UPDATING_TEST_TAG)
        )
    }
  }
}

@VisibleForTesting
object LoadingRefreshButtonTestTags {
  const val UPDATED_TEST_TAG = "updated"
  const val UPDATING_TEST_TAG = "updating"
}

