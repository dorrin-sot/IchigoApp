package com.example.ichigoapp.service

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ichigoapp.model.Fruit

@Database(entities = [Fruit::class], exportSchema = false, version = 2)
abstract class AppDatabase : RoomDatabase() {
  abstract fun fruitDao(): FruitDao
}
