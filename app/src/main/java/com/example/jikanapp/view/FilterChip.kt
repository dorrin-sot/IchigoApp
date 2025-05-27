package com.example.jikanapp.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun FilterChip(
  label: String,
  onDismiss: () -> Unit
) {
  InputChip(
    selected = true,
    onClick = onDismiss,
    label = {
      Text(
        label,
        fontWeight = FontWeight.Bold
      )
    },
    avatar = {
      Icon(
        Icons.Default.FilterAlt, "Filter",
        modifier = Modifier.size(InputChipDefaults.AvatarSize)
      )
    },
    trailingIcon = {
      Icon(
        Icons.Default.Close,
        contentDescription = "Remove filter",
        Modifier.size(InputChipDefaults.AvatarSize)
      )
    },
    modifier = Modifier.padding(horizontal = 10.dp)
  )
}
