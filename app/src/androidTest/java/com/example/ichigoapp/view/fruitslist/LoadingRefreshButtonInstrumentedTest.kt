package com.example.ichigoapp.view.fruitslist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ichigoapp.TestMainActivity
import com.example.ichigoapp.service.DatabaseStatus
import com.example.ichigoapp.ui.theme.IchigoTheme
import com.example.ichigoapp.view.LoadingRefreshButton
import com.example.ichigoapp.view.LoadingRefreshButtonTestTags.UPDATED_TEST_TAG
import com.example.ichigoapp.view.LoadingRefreshButtonTestTags.UPDATING_TEST_TAG
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoadingRefreshButtonInstrumentedTest {
  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @get:Rule
  val composeTestRule = createAndroidComposeRule(TestMainActivity::class.java)

  @Before
  fun setUp() = hiltRule.inject()

  @Test
  fun loadingRefreshButton_shouldRenderUpdatedCorrectly() {
    composeTestRule.run {
      setContent {
        IchigoTheme {
          LoadingRefreshButton(
            databaseStatus = DatabaseStatus.Updated,
            onRefresh = {}
          )
        }
      }

      waitForIdle()

      onNodeWithTag(UPDATED_TEST_TAG, useUnmergedTree = true)
        .assertExists()
        .assertIsDisplayed()
        .onParent()
        .assertIsEnabled()
    }
  }

  @Test
  fun loadingRefreshButton_shouldRenderUpdatingCorrectly() {
    composeTestRule.run {
      setContent {
        IchigoTheme {
          LoadingRefreshButton(
            databaseStatus = DatabaseStatus.Updating,
            onRefresh = {}
          )
        }
      }

      waitForIdle()

      onNodeWithTag(UPDATING_TEST_TAG, useUnmergedTree = true)
        .assertExists()
        .assertIsDisplayed()
        .onParent()
        .assertIsNotEnabled()
    }
  }

  @Test
  fun loadingRefreshButton_shouldCallOnRefreshCorrectly() {
    var switch = false
    composeTestRule.run {
      setContent {
        IchigoTheme {
          LoadingRefreshButton(
            databaseStatus = DatabaseStatus.Updated,
            onRefresh = { switch = true }
          )
        }
      }

      waitForIdle()

      assert(!switch)

      onNodeWithTag(UPDATED_TEST_TAG, useUnmergedTree = true)
        .assertExists()
        .assertIsDisplayed()
        .onParent()
        .assertIsEnabled()
        .performClick()

      waitForIdle()

      assert(switch)
    }
  }
}
