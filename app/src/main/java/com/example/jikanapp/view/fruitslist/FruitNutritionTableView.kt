package com.example.jikanapp.view.fruitslist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.jikanapp.model.Fruit
import com.example.jikanapp.view.TableView

@Composable
internal fun FruitNutritionTableView(
  fruit: Fruit,
  modifier: Modifier
) {
  TableView(
    heading = "Nutritional facts",
    rows = fruit.nutritions.toMap(),
    modifier = modifier
  )
}
