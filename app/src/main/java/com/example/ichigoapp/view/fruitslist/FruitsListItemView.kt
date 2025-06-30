package com.example.ichigoapp.view.fruitslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ichigoapp.model.Fruit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FruitsListItemView(fruit: Fruit, modifier: Modifier) {
  ListItem(
    shadowElevation = 3.dp,
    tonalElevation = 1.dp,
    headlineContent = { Text(fruit.name, fontWeight = FontWeight.ExtraBold) },
    trailingContent = {
      FruitNutritionTableView(
        fruit,
        modifier = Modifier.width(IntrinsicSize.Max)
      )
    },
    supportingContent = { FruitAncestryView(fruit) },
    modifier = Modifier
      .padding(5.dp)
      .clickable(onClick = {})
      .then(modifier)
  )
}
