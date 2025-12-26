package com.android.fetchtest.presentation

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.android.fetchtest.presentation.composable.TopAppBar
import com.android.fetchtest.presentation.mainActivity.ItemScreen
import com.android.fetchtest.presentation.mainActivity.MainViewModel
import com.android.fetchtest.presentation.mainActivity.MainActivity
import com.android.fetchtest.presentation.tag.TestTags
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
                        Scaffold(topBar = { TopAppBar(title = "Testing Fetch") }) {
                            val mainViewModel: MainViewModel = hiltViewModel()
                            val state by mainViewModel.resource.collectAsStateWithLifecycle()
                            ItemScreen(modifier = Modifier.padding(paddingValues = it), resource = state) { mainViewModel.onEvent(OnEvent.Retry) }
                        }
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
        composeTestRule.onNodeWithTag(testTag = TestTags.RETRY).assertIsDisplayed()
    }

}
