package com.example.jikanapp

import android.content.Context
import androidx.room.Room
import com.example.jikanapp.service.AppDatabase
import com.example.jikanapp.service.FruitApi
import com.example.jikanapp.service.RetrofitHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JikanModule {
  @Provides
  @Singleton
  fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
    Room
      .databaseBuilder(context, AppDatabase::class.java, "jikan-db")
      .fallbackToDestructiveMigration(dropAllTables = true)
      .build()

  @Provides
  @Singleton
  fun provideFruitApi(): FruitApi = RetrofitHelper.fruitApi
}
