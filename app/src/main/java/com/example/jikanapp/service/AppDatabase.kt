package com.example.jikanapp.service

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jikanapp.model.Fruit

@Database(entities = [Fruit::class], exportSchema = false, version = 2)
abstract class AppDatabase : RoomDatabase() {
  abstract fun fruitDao(): FruitDao
}
