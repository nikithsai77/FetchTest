package com.android.fetchtest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.fetchtest.common.ClickEvent
import com.android.fetchtest.common.Resource
import com.android.fetchtest.ui.theme.FetchTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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

@PreviewLightDark
@Composable
fun GreetingPreview() {
    FetchTestTheme {
        ItemScreen(Resource.Loading) {}
    }
}