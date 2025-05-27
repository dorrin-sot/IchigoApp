package com.example.jikanapp.views

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.jikanapp.capitalize
import com.example.jikanapp.viewmodel.FruitsListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FruitsListView(
  modifier: Modifier = Modifier,
  viewModel: FruitsListViewModel
) {
  val fruits = viewModel.fruits
  LaunchedEffect(Unit) {
    viewModel.fetchFruits()
  }

  LazyColumn(
    modifier = Modifier
      .pullToRefresh(
        isRefreshing = false,
        state = rememberPullToRefreshState(),
        onRefresh = { viewModel.fetchFruits() }
      )
      .padding(5.dp)
      .then(modifier),
  ) {
    fruits
      .groupBy { it.name.uppercase().trim().first() }
      .toSortedMap()
      .forEach { initial, list ->
        stickyHeader(initial) {
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

        items(list, key = { it.hashCode() + System.currentTimeMillis() }) { fruit ->
          ListItem(
            shadowElevation = 3.dp,
            tonalElevation = 1.dp,
            headlineContent = { Text(fruit.name, fontWeight = FontWeight.ExtraBold) },
            trailingContent = {
              Column(
                modifier = Modifier
                  .width(IntrinsicSize.Max)
                  .border(
                    2.dp,
                    Color.DarkGray,
                    RoundedCornerShape(5.dp)
                  ),
              ) {
                Text(
                  "Nutritional facts",
                  fontWeight = FontWeight.Bold,
                  color = Color.DarkGray,
                  textAlign = TextAlign.Center,
                  modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 3.dp)
                    .fillMaxWidth()
                )
                fruit.nutritions.toMap()
                  .forEach { (k, v) ->
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
            },
            supportingContent = {
              fun filterByAncestor(key: String, value: String) =
                viewModel.filterByAncestor(key, value)

              Column {
                Row {
                  Text(
                    fruit.family,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                      .clickable(onClick = { filterByAncestor("family", fruit.family) })
                  )
                  Text(" | ", fontWeight = FontWeight.ExtraLight)
                  Text(
                    fruit.order,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                      .clickable(onClick = { filterByAncestor("order", fruit.order) })
                  )
                  Text(" | ", fontWeight = FontWeight.ExtraLight)
                  Text(
                    fruit.genus,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                      .clickable(onClick = { filterByAncestor("genus", fruit.genus) })
                  )
                }
              }
            },
            modifier = Modifier
              .padding(5.dp)
              .clickable(onClick = {})
          )
        }
      }
  }
}
