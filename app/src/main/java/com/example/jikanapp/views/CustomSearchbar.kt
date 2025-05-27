package com.example.jikanapp.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun CustomSearchbar(
  value: String?,
  onValueChange: (String?) -> Unit,
) {
  TextField(
    value = value ?: "",
    onValueChange = onValueChange,
    modifier = Modifier
      .zIndex(10f)
      .padding(5.dp)
      .fillMaxSize(),
    shape = RoundedCornerShape(100),
    placeholder = { Text("Search fruits...") },
    leadingIcon = { Icon(Icons.Default.Search, "Search") },
    trailingIcon = {
      IconButton(onClick = { onValueChange(null) }) {
        Icon(Icons.Default.Close, "Close Search")
      }
    },
  )
}
