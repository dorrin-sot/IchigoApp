package com.example.jikanapp.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.jikanapp.models.Fruit
import com.example.jikanapp.viewmodel.FruitsListViewModel

@Composable
fun FruitAncestryView(fruit: Fruit, viewModel: FruitsListViewModel) {
  Column {
    Row {
      Text(
        fruit.family,
        fontWeight = FontWeight.Light,
        modifier = Modifier
          .clickable(onClick = { viewModel.filterByAncestor("family", fruit.family) })
      )
      Text(" | ", fontWeight = FontWeight.ExtraLight)
      Text(
        fruit.order,
        fontWeight = FontWeight.Light,
        modifier = Modifier
          .clickable(onClick = { viewModel.filterByAncestor("order", fruit.order) })
      )
      Text(" | ", fontWeight = FontWeight.ExtraLight)
      Text(
        fruit.genus,
        fontWeight = FontWeight.Light,
        modifier = Modifier
          .clickable(onClick = { viewModel.filterByAncestor("genus", fruit.genus) })
      )
    }
  }
}