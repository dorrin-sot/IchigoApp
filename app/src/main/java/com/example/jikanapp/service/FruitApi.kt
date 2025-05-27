package com.example.jikanapp.service

import com.example.jikanapp.model.Fruit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FruitApi {
  @GET("/api/fruit/all")
  fun fetchAll(): Call<List<Fruit>>

  @GET("/api/fruit/{id}")
  fun fetchFruitById(@Path("id") id: Int)

  @GET("/api/fruit/{name}")
  fun fetchFruitByName(@Path("name") name: String)
}