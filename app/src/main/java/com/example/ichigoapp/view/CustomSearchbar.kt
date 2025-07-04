package com.example.ichigoapp.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun CustomSearchbar(
  value: String?,
  onValueChange: (String?) -> Unit,
) {
  OutlinedTextField(
    value = value ?: "",
    onValueChange = onValueChange,
    modifier = Modifier
      .zIndex(10f)
      .padding(5.dp)
      .wrapContentHeight()
      .fillMaxWidth(),
    shape = RoundedCornerShape(100),
    placeholder = { Text("Search fruits...") },
    leadingIcon = { Icon(Icons.Default.Search, "Search") },
    trailingIcon = {
      IconButton(onClick = { onValueChange("") }) {
        Icon(Icons.Default.Close, "Close Search")
      }
    },
    colors = OutlinedTextFieldDefaults.colors().copy(
      unfocusedContainerColor = Color.White.copy(alpha = .9f),
      focusedContainerColor = Color.White,
    )
  )
}
