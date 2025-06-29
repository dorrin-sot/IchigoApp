package com.example.ichigoapp.view.fruitslist

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.ichigoapp.TestMainActivity
import com.example.ichigoapp.TestDataGenerator.generateFruit
import com.example.ichigoapp.model.AncestryLevel
import com.example.ichigoapp.service.Repository
import com.example.ichigoapp.ui.theme.IchigoTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coVerify
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
  lateinit var repoMock: Repository

  @Before
  fun setUp() {
    hiltRule.inject()
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
