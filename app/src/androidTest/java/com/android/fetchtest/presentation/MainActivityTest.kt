package com.android.fetchtest.presentation

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.fetchtest.di.Module
import com.android.fetchtest.ui.theme.FetchTestTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(Module::class)
class MainActivityTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.setContent {
                FetchTestTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val itemViewModel: ItemViewModel = hiltViewModel()
                        val state by itemViewModel.resource.collectAsStateWithLifecycle()
                        ItemScreen(state) { itemViewModel.onEvent(ClickEvent.Retry) }
                    }
                }
            }
        }
    }

    @Test
    fun success() {
        composeTestRule.onNodeWithTag(testTag = TestTags.SUCCESS).assertIsDisplayed()
    }

    @Test
    fun retry() {
        composeTestRule.onNodeWithTag(testTag = TestTags.RETRY).assertIsDisplayed()
        composeTestRule.onNodeWithText(text = TestTags.RETRY).performClick()
    }

}