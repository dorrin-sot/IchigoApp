package com.example.ichigoapp

import androidx.annotation.VisibleForTesting
import com.example.ichigoapp.model.Fruit
import com.example.ichigoapp.model.Nutrition

@VisibleForTesting
object TestDataGenerator {
  fun generateNFruits(n: Int): List<Fruit> {
    val fruit = Fruit(
      id = 3,
      name = "Strawberry",
      family = "Rosaceae",
      order = "Rosales",
      genus = "Fragaria",
      nutritions = Nutrition(
        calories = 29f,
        fat = 0.4f,
        sugar = 5.4f,
        carbohydrates = 5.5f,
        protein = 0.8f,
      )
    )
    return List(n) { fruit.copy(id = it) }
  }

  fun generateFruit(): Fruit = generateNFruits(1).first()
}