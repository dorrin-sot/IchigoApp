package com.example.jikanapp.services

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jikanapp.models.Fruit

@Database(entities = [Fruit::class], exportSchema = false, version = 2)
abstract class AppDatabase : RoomDatabase() {
  abstract fun fruitDao(): FruitDao
}
