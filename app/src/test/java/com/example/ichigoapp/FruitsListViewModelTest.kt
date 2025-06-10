package com.example.ichigoapp

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ichigoapp.model.AncestryLevel
import com.example.ichigoapp.service.Repository
import com.example.ichigoapp.viewmodel.FruitsListViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockkClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FruitsListViewModelTest {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

  private lateinit var viewModel: FruitsListViewModel
  private lateinit var repoMock: Repository

  @Before
  fun setUp() {
    Dispatchers.setMain(testDispatcher)
    repoMock = mockkClass(Repository::class)
    viewModel = FruitsListViewModel(repoMock)
  }

  @After
  fun tearDown() = Dispatchers.resetMain()

  @Test
  fun `FruitsListViewModel fetchFruits should call Repository fetchFruits`() = runTest {
    coEvery { repoMock.fetchFruits() } coAnswers { }
    viewModel.fetchFruits()
    advanceUntilIdle()
    coVerify { repoMock.fetchFruits() }
  }

  @Test
  fun `FruitsListViewModel filterByAncestor should call Repository filterByAncestor`() = runTest {
    val key = AncestryLevel.Genus
    val value = "Rosalie"
    coEvery { repoMock.filterByAncestor(any(), any()) } coAnswers { }
    viewModel.filterByAncestor(key, value)
    advanceUntilIdle()
    coVerify { repoMock.filterByAncestor(key, value) }
  }

  @Test
  fun `FruitsListViewModel removeFilter should call Repository removeFilter`() = runTest {
    coEvery { repoMock.removeFilter() } coAnswers { }
    viewModel.removeFilter()
    advanceUntilIdle()
    coVerify { repoMock.removeFilter() }
  }

  @Test
  fun `FruitsListViewModel search should call Repository search`() = runTest {
    val q = "hello"
    coEvery { repoMock.search(any()) } coAnswers { }
    viewModel.search(q)
    advanceUntilIdle()
    coVerify { repoMock.search(q) }
  }

  @Test
  fun `FruitsListViewModel search should cancel previous search`() = runTest {
    val query1 = "banana"
    val query2 = "orange"

    coEvery { repoMock.search(any()) } coAnswers { delay(100_000) }

    viewModel.search(query1)
    advanceTimeBy(10_000)

    val job1 = viewModel.searchJob

    viewModel.search(query2)
    advanceTimeBy(10_000)

    val job2 = viewModel.searchJob

    assert(job1!!.isCancelled) { "First search job should be cancelled" }
    assert(job2!!.isActive || job2.isCompleted) { "Second search job should be active or completed" }

    coVerify { repoMock.search(query1) }
    coVerify { repoMock.search(query2) }
  }
}