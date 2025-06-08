package com.example.ichigoapp

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ichigoapp.model.Fruit
import com.example.ichigoapp.model.Nutrition
import com.example.ichigoapp.service.AppDatabase
import com.example.ichigoapp.service.FruitApi
import com.example.ichigoapp.service.Repository
import io.mockk.mockkClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryTest {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

  private lateinit var repository: Repository
  private lateinit var apiMock: FruitApi
  private lateinit var dbMock: AppDatabase

  @Before
  fun setUp() {
    println("setUp")
    Dispatchers.setMain(testDispatcher)
    apiMock = mockkClass(FruitApi::class)
    dbMock = mockkClass(AppDatabase::class)
    repository = Repository(dbMock, apiMock)
  }

  @After
  fun tearDown() {
    println("tearDown")
    Dispatchers.resetMain()
  }

  @Test
  fun `Repository _fruits getter should return fruits value`() = runTest {
    repository.fruits.addAll(generateNFruits(5))
    assert(repository.fruits.size == 5)
    assert(repository._fruits.size == 5)
    assert(repository._fruits.toList() == repository.fruits.toList())
  }

  @Test
  fun `Repository _fruits setter should clear fruits and add all new elements`() = runTest {
    repository.fruits.addAll(generateNFruits(5))

    repository._fruits = generateNFruits(10)
    assert(repository.fruits.size == 10)
    assert(repository._fruits.size == 10)
    assert(repository._fruits.toList() == repository.fruits.toList())
  }

  fun generateNFruits(n: Int): List<Fruit> {
    val fruit = Fruit(
      id = 0,
      name = "TODO()",
      family = "TODO()",
      order = "TODO()",
      genus = "TODO()",
      nutritions = Nutrition(calories = 1f, fat = 1f, sugar = 1f, carbohydrates = 1f, protein = 1f)
    )
    return List(n) { fruit.copy(id = it) }
    repository.fruits.addAll(List(n) { fruit.copy(id = it) })
  }
}