package com.example.ichigoapp.model

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
