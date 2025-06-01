package com.example.ichigoapp.view.fruitslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ichigoapp.model.AncestryLevel
import com.example.ichigoapp.model.Fruit
import com.example.ichigoapp.viewmodel.FruitsListViewModel

@Composable
internal fun FruitAncestryView(fruit: Fruit) {
  val viewModel = hiltViewModel<FruitsListViewModel>()

  Column {
    Row {
      Text(
        fruit.family,
        fontWeight = FontWeight.Light,
        modifier = Modifier
          .clickable(onClick = { viewModel.filterByAncestor(AncestryLevel.Family, fruit.family) })
      )
      Text(" | ", fontWeight = FontWeight.ExtraLight)
      Text(
        fruit.order,
        fontWeight = FontWeight.Light,
        modifier = Modifier
          .clickable(onClick = { viewModel.filterByAncestor(AncestryLevel.Order, fruit.order) })
      )
      Text(" | ", fontWeight = FontWeight.ExtraLight)
      Text(
        fruit.genus,
        fontWeight = FontWeight.Light,
        modifier = Modifier
          .clickable(onClick = { viewModel.filterByAncestor(AncestryLevel.Genus, fruit.genus) })
      )
    }
  }
}