package com.android.fetchtest.presentation.mainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.fetchtest.domain.util.DataError
import com.android.fetchtest.domain.model.FetchItem
import com.android.fetchtest.domain.util.Result
import com.android.fetchtest.presentation.components.ItemScreen
import com.android.fetchtest.presentation.components.TopAppBar
import com.android.fetchtest.presentation.theme.FetchTestTheme
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
                    Scaffold(
                        topBar = {
                            TopAppBar(title = "Fetch")
                        }
                    ) {
                        val mainViewModel: MainViewModel = hiltViewModel()
                        val state by mainViewModel.resource.collectAsStateWithLifecycle()
                        val onRetry = retain { { mainViewModel.onEvent(OnEvent.Retry) } }
                        ItemScreen(
                            modifier = Modifier.padding(paddingValues = it),
                            resource = state,
                            onRetry = onRetry
                        )
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun GreetingPreview(
    @PreviewParameter(provider = ResourceProvider::class) resource: Result<Map<Int, List<FetchItem>>, DataError>
) {
    FetchTestTheme {
        ItemScreen(resource = resource) {}
    }
}

class ResourceProvider : PreviewParameterProvider<Result<Map<Int, List<FetchItem>>, DataError>> {
    override val values: Sequence<Result<Map<Int, List<FetchItem>>, DataError>> = resourceList
}

val resourceList = sequenceOf(
    Result.Loading,
    Result.Success(
        data = mapOf(
            1 to listOf(
                FetchItem(listId = 1, name = "sample 0", id = 1),
                FetchItem(listId = 1, name = "sample 1", id = 2),
                FetchItem(listId = 1, name = "sample 2", id = 0)
            )
        )
    ),
    Result.Error(error = DataError.NetworkError.NO_INTERNET)
)
