package com.example.jikanapp.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Suppress("SpellCheckingInspection")
@Entity
data class Fruit(
  @PrimaryKey val id: Int,
  val name: String,
  val family: String,
  val order: String,
  val genus: String,
  @Embedded(prefix = "nutritions_") val nutritions: Nutrition
) {
  val ancestry: String get() = "$family | $order | $genus"
}

data class Nutrition(
  val calories: Float,
  val fat: Float,
  val sugar: Float,
  val carbohydrates: Float,
  val protein: Float,
) {
  fun toMap(): Map<String, Float> = mapOf(
    "calories" to calories,
    "fat" to fat,
    "sugar" to sugar,
    "carbohydrates" to carbohydrates,
    "protein" to protein,
  )
}
