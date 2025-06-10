package com.example.ichigoapp.service

import com.example.ichigoapp.model.Fruit
import retrofit2.http.GET
import retrofit2.http.Path

interface FruitApi {
  @GET("/api/fruit/all")
  suspend fun fetchAll(): List<Fruit>

  @GET("/api/fruit/{id}")
  suspend fun fetchFruitById(@Path("id") id: Int)

  @GET("/api/fruit/{name}")
  suspend fun fetchFruitByName(@Path("name") name: String)
}