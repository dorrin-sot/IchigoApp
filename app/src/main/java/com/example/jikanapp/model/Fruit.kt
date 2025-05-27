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
)
