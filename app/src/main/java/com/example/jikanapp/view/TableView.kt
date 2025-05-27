package com.example.jikanapp.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.jikanapp.capitalize

@Composable
fun TableView(
  heading: String,
  rows: Map<String, Any>,
  modifier: Modifier
) {
  Column(
//      .width(IntrinsicSize.Max)
    modifier = Modifier
      .border(
        2.dp,
        Color.DarkGray,
        RoundedCornerShape(5.dp)
      )
      .then(modifier),
  ) {
    Text(
      heading,
      fontWeight = FontWeight.Bold,
      color = Color.DarkGray,
      textAlign = TextAlign.Center,
      modifier = Modifier
        .padding(horizontal = 5.dp, vertical = 3.dp)
        .fillMaxWidth()
    )
    rows.forEach { (k, v) ->
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
          .fillMaxWidth()
          .drawBehind {
            drawLine(
              color = Color.LightGray,
              start = Offset(0f, 0f),
              end = Offset(size.width, 0f),
              strokeWidth = 1.dp.toPx()
            )
          }
      ) {
        Text(
          k.capitalize(),
          fontWeight = FontWeight.Normal,
          color = Color.DarkGray,
          maxLines = 1,
          modifier = Modifier
            .wrapContentWidth()
            .padding(horizontal = 5.dp)
        )
        Text(
          "$v",
          fontWeight = FontWeight.Light,
          color = Color.DarkGray,
          maxLines = 1,
          modifier = Modifier
            .wrapContentWidth()
            .padding(horizontal = 5.dp)
        )
      }
    }
  }
}