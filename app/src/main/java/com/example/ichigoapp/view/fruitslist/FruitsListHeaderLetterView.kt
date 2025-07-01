package com.example.ichigoapp.view.fruitslist

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
internal fun FruitsListHeaderLetterView(initial: Char) {
  Surface(
    color = Color(0xFFC1AFEA),
    shape = RoundedCornerShape(10.dp),
    shadowElevation = 5.dp,
    modifier = Modifier.fillMaxWidth()
  ) {
    Text(
      "$initial",
      color = Color(0xFF633FB5),
      fontWeight = FontWeight.Black,
      modifier = Modifier.padding(horizontal = 5.dp)
    )
  }
}