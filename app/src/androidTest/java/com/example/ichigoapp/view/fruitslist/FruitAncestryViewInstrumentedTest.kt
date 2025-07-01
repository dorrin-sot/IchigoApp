package com.example.ichigoapp.view.fruitslist

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.ichigoapp.TestDataGenerator.generateFruit
import com.example.ichigoapp.TestMainActivity
import com.example.ichigoapp.model.AncestryLevel
import com.example.ichigoapp.service.AppDatabase
import com.example.ichigoapp.service.FruitApi
import com.example.ichigoapp.service.Repository
import com.example.ichigoapp.ui.theme.IchigoTheme
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
import javax.inject.Inject

@HiltAndroidTest
class FruitAncestryViewInstrumentedTest {
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
  fun fruitAncestryView_shouldRenderAncestryCorrectly() {
    composeTestRule.run {
      val fruit = generateFruit()
      setContent {
        IchigoTheme {
          FruitAncestryView(fruit)
        }
      }

      waitForIdle()

      arrayOf(fruit.family, fruit.order, fruit.genus).forEach { text ->
        onNodeWithText(text).run {
          assertIsDisplayed()
          assertHasClickAction()
        }
      }
    }
  }

  @Test
  fun fruitAncestryView_ancestryClickShouldApplyFilter() {
    composeTestRule.run {
      coEvery { repoMock.fetchFruits() } coAnswers {}

      val fruit = generateFruit()
      setContent {
        IchigoTheme {
          FruitAncestryView(fruit)
        }
      }

      waitForIdle()

      mapOf(
        AncestryLevel.Family to fruit.family,
        AncestryLevel.Order to fruit.order,
        AncestryLevel.Genus to fruit.genus
      ).forEach { k, v ->
        onNodeWithText(v).run {
          performClick()
          waitForIdle()
          coVerify(exactly = 1) { repoMock.filterByAncestor(k, v) }
        }
      }
    }
  }
}
