package com.example.ichigoapp

import android.content.Context
import androidx.room.Room
import com.example.ichigoapp.service.AppDatabase
import com.example.ichigoapp.service.FruitApi
import com.example.ichigoapp.service.RetrofitHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object IchigoModule {
  @Provides
  @Singleton
  fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
    Room
      .databaseBuilder(context, AppDatabase::class.java, "ichigo-db")
      .fallbackToDestructiveMigration(dropAllTables = true)
      .build()

  @Provides
  @Singleton
  fun provideFruitApi(): FruitApi = RetrofitHelper.fruitApi
}
