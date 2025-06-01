package com.example.ichigoapp.service

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.ichigoapp.model.AncestryLevel
import com.example.ichigoapp.model.Fruit

@Dao
interface FruitDao {
  @Query("select * from fruit")
  fun getAll(): List<Fruit>

  @Insert(onConflict = OnConflictStrategy.ABORT)
  fun insertAll(vararg fruit: Fruit)

  @Delete
  fun delete(fruit: Fruit)

  @Delete
  fun deleteSome(vararg fruit: Fruit)

  @Transaction()
  @Query("delete from fruit where 1")
  fun deleteAll()

  @Query("select * from fruit where family = :value")
  fun filterFamily(value: String): List<Fruit>

  @Query("select * from fruit where `order` = :value")
  fun filterOrder(value: String): List<Fruit>

  @Query("select * from fruit where genus = :value")
  fun filterGenus(value: String): List<Fruit>

  @Query("select * from fruit where name like :query")
  fun getAllWithSearchQuery(query: String): List<Fruit>

  @Query("select * from fruit where name like :query and family = :family")
  fun filterFamilyWithSearchQuery(query: String, family: String): List<Fruit>

  @Query("select * from fruit where name like :query and `order` = :order")
  fun filterOrderWithSearchQuery(query: String, order: String): List<Fruit>

  @Query("select * from fruit where name like :query and genus = :genus")
  fun filterGenusWithSearchQuery(query: String, genus: String): List<Fruit>

  fun get(filter: Pair<AncestryLevel, String>?, query: String?): List<Fruit> =
    when (filter?.first) {
      AncestryLevel.Family -> {
        if (query == null) filterFamily(filter.second)
        else filterFamilyWithSearchQuery("%${query}%", filter.second)
      }

      AncestryLevel.Order -> {
        if (query == null) filterOrder(filter.second)
        else filterOrderWithSearchQuery("%${query}%", filter.second)
      }

      AncestryLevel.Genus -> {
        if (query == null) filterGenus(filter.second)
        else filterGenusWithSearchQuery("%${query}%", filter.second)
      }

      null -> {
        if (query == null) getAll()
        else getAllWithSearchQuery("%${query}%")
      }
    }
}