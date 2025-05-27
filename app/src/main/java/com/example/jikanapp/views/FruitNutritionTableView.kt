package com.example.jikanapp.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.jikanapp.models.Fruit

@Composable
fun FruitNutritionTableView(
  fruit: Fruit,
  modifier: Modifier
) {
  TableView(
    heading = "Nutritional facts",
    rows = fruit.nutritions.toMap(),
    modifier = modifier
  )
}
