package com.example.ichigoapp.service

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ichigoapp.model.AncestryLevel
import com.example.ichigoapp.model.Fruit
import com.example.ichigoapp.model.Nutrition
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockkClass
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.anyOrNull

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryTest {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

  private lateinit var repository: Repository
  private lateinit var apiMock: FruitApi
  private lateinit var dbMock: AppDatabase
  private lateinit var fruitDaoMock: FruitDao

  @Before
  fun setUp() {
    Dispatchers.setMain(testDispatcher)
    apiMock = mockkClass(FruitApi::class)

    dbMock = mockkClass(AppDatabase::class)
    fruitDaoMock = mockkClass(FruitDao::class)

    every { dbMock.fruitDao() } returns (fruitDaoMock)

    repository = Repository(dbMock, apiMock, testDispatcher)
  }

  @After
  fun tearDown() = Dispatchers.resetMain()

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

  @Test
  fun `Repository updateFruitsFromDb with filter and null query`() = runTest {
    val argsList = listOf(
      Triple(null, null, generateNFruits(10)),
      Triple(null, "app", generateNFruits(5)),
      Triple(AncestryLevel.Genus to "Rosa", null, generateNFruits(5)),
      Triple(AncestryLevel.Genus to "Rosa", "app", generateNFruits(2)),
      Triple(AncestryLevel.Genus to "Rosa", "", generateNFruits(10)),
    )

    argsList
      .forEachIndexed { idx, args ->
        val (filter, query, results) = args
        if (idx > 0) setUp()

        repository.filter.value = filter
        repository.query.value = query

        val dao = dbMock.fruitDao()

        coEvery { dao.get(null, null) } coAnswers { results }
        coEvery { dao.get(null, any()) } coAnswers { results }
        coEvery { dao.get(any(), null) } coAnswers { results }
        coEvery { dao.get(any(), any()) } coAnswers { results }

        repository.updateFruitsFromDb()
        advanceUntilIdle()

        coVerify(exactly = 1) { dao.get(filter, query?.ifEmpty { null }) }
        if (idx < argsList.size - 1) tearDown()
      }
  }

  @Test
  fun `Repository updateDbWithResult should call db deleteAll and insertAll`() = runTest {
    val fruits = generateNFruits(10)

    coEvery { fruitDaoMock.deleteAll() } coAnswers {}
    coEvery { fruitDaoMock.insertAll(*anyVararg<Fruit>()) } coAnswers {}

    repository.updateDbWithResult(fruits)
    advanceUntilIdle()

    coVerify { fruitDaoMock.deleteAll() }
    coVerify { fruitDaoMock.insertAll(*fruits.toTypedArray()) }
  }

  @Test
  fun `Repository apiFetchFruits`() = runTest {
    val fruits = generateNFruits(10)
    coEvery { apiMock.fetchAll() } coAnswers { fruits }
    assert(repository.apiFetchFruits() == fruits)
    coVerify { apiMock.fetchAll() }
  }


  @Test
  fun `Repository fetchFruits correct behavior`() = runTest(testDispatcher) {
    val dbFruits = generateNFruits(10)
    val apiFruits = generateNFruits(20)

    val stage1Deferred = CompletableDeferred<List<Fruit>>()
    val stage2Deferred = CompletableDeferred<List<Fruit>>()
    val stage3Deferred = CompletableDeferred<List<Fruit>>()

    coEvery { fruitDaoMock.get(anyOrNull(), anyOrNull()) } coAnswers {
      if (stage1Deferred.isActive) stage1Deferred.await()
      else stage3Deferred.await()
    }
    coEvery { apiMock.fetchAll() } coAnswers { stage2Deferred.await() }
    coEvery { fruitDaoMock.deleteAll() } coAnswers {}
    coEvery { fruitDaoMock.insertAll(*varargAny<Fruit> { true }) } coAnswers {}

    val job = async { repository.fetchFruits() }
    advanceUntilIdle()

    assert(repository.fruits.isEmpty()) { "Fruits should be empty but is ${repository.fruits.size}" }

    stage1Deferred.complete(dbFruits)

    advanceUntilIdle()
    assert(repository.fruits.size == dbFruits.size) { "Fruits should be the db amount but is ${repository.fruits.size}" }
    assert(repository.databaseStatus.value == DatabaseStatus.Updating)

    stage2Deferred.complete(apiFruits) // DB update
    advanceUntilIdle()
    assert(repository.fruits.size == dbFruits.size) { "Fruits should be the db amount but is ${repository.fruits.size}" }

    stage3Deferred.complete(apiFruits)
    advanceUntilIdle()
    assert(repository.fruits.size == apiFruits.size) { "Fruits should be the api amount but is ${repository.fruits.size}" }
    assert(repository.databaseStatus.value == DatabaseStatus.Updated)

    job.await()
    coVerify(exactly = 1) { fruitDaoMock.deleteAll() }
    coVerify(exactly = 1) { fruitDaoMock.insertAll(*varargAny<Fruit> { true }) }
    coVerify(exactly = 2) { fruitDaoMock.get(null, null) }
  }

  private companion object {
    fun generateNFruits(n: Int): List<Fruit> {
      val fruit = Fruit(
        id = 0,
        name = "TODO()",
        family = "TODO()",
        order = "TODO()",
        genus = "TODO()",
        nutritions = Nutrition(
          calories = 1f,
          fat = 1f,
          sugar = 1f,
          carbohydrates = 1f,
          protein = 1f
        )
      )
      return List(n) { fruit.copy(id = it) }
    }
  }
}