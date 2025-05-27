package com.example.jikanapp.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
  private const val BASE_URL = "https://www.fruityvice.com/"

  val fruitApi: FruitApi by lazy {
    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(FruitApi::class.java)
  }
}