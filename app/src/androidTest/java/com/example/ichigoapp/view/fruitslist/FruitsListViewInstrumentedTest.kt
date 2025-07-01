package com.example.ichigoapp.view.fruitslist

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ichigoapp.TestDataGenerator.generateNFruits
import com.example.ichigoapp.TestMainActivity
import com.example.ichigoapp.service.AppDatabase
import com.example.ichigoapp.service.FruitApi
import com.example.ichigoapp.service.Repository
import com.example.ichigoapp.ui.theme.IchigoTheme
import com.example.ichigoapp.view.fruitslist.FruitsListViewTestTags.FRUITS_LIST_TEST_TAG
import com.example.ichigoapp.view.fruitslist.FruitsListViewTestTags.fruitItemTestTag
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class FruitsListViewInstrumentedTest {
  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @get:Rule
  val composeTestRule = createAndroidComposeRule(TestMainActivity::class.java)

  @Inject
  lateinit var db: AppDatabase

  @Inject
  lateinit var api: FruitApi

  @BindValue
  @JvmField
  var repoMock: Repository = mockk()

  @Before
  fun setUp() {
    hiltRule.inject()
    repoMock = spyk(Repository(db, api))
  }

  @Test
  fun fruitListView_shouldRenderItemsCorrectly() {
    composeTestRule.run {
      val fruits = generateNFruits(20)
      coEvery { repoMock.fetchFruits() } coAnswers { repoMock._fruits = fruits }

      setContent {
        IchigoTheme {
          FruitsListView()
        }
      }

      waitForIdle()

      fruits.forEach {
        onNodeWithTag(FRUITS_LIST_TEST_TAG)
          .performScrollToNode(hasTestTag(fruitItemTestTag(it)))
          .assertExists()
          .assertIsDisplayed()
      }
    }
  }

  @Test
  fun fruitListView_shouldFetchFruitsAutomaticallyOnStart() {
    composeTestRule.run {
      val fruits = generateNFruits(20)
      coEvery { repoMock.fetchFruits() } coAnswers { repoMock._fruits = fruits }

      setContent {
        IchigoTheme {
          FruitsListView()
        }
      }

      waitForIdle()

      coVerify(exactly = 1) { repoMock.fetchFruits() }
    }
  }

  @Test
  fun fruitListView_pullToRefreshShouldUpdateFruitsCorrectly() {
    composeTestRule.run {
      val fruits = generateNFruits(6)
      var index = 3
      coEvery { repoMock.fetchFruits() } coAnswers { repoMock._fruits = fruits.subList(0, index) }

      setContent {
        IchigoTheme {
          FruitsListView()
        }
      }

      waitForIdle()

      onNodeWithTag("fruits-list").run {
        // initially 3 items
        for (i in 0 until index + 1) onChildAt(i).assertExists()

        onChildAt(index + 1).assertDoesNotExist()

        // pull to refresh
        index = 6
        performTouchInput { swipeDown() }
        waitForIdle()

        // now 20 items
        for (i in 0 until index + 1) onChildAt(i).assertExists()

        coVerify(exactly = 2) { repoMock.fetchFruits() }
      }
    }
  }
}
