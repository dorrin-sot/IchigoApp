package com.example.ichigoapp.view.fruitslist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ichigoapp.model.Fruit
import com.example.ichigoapp.view.TableView

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
