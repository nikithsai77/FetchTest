package com.android.fetchtest.presentation.mainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.fetchtest.data.Item
import com.android.fetchtest.domain.DataError
import com.android.fetchtest.domain.Result
import com.android.fetchtest.presentation.ClickEvent
import com.android.fetchtest.presentation.composable.TopAppBar
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
                    Scaffold(topBar = { TopAppBar(title = "Fetch") }) {
                        val itemViewModel: ItemViewModel = hiltViewModel()
                        val state by itemViewModel.resource.collectAsStateWithLifecycle()
                        val clickEvent = remember { { itemViewModel.onEvent(ClickEvent.Retry) } }
                        ItemScreen(paddingValue = it, resource = state, clickEvent = clickEvent)
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun GreetingPreview(
    @PreviewParameter(provider = ResourceProvider::class) resource: Result<Map<Int, List<Item>>, DataError>
) {
    FetchTestTheme {
        ItemScreen(paddingValue = PaddingValues(all = 10.dp), resource = resource) {}
    }
}

class ResourceProvider: PreviewParameterProvider<Result<Map<Int, List<Item>>,DataError>> {
    override val values: Sequence<Result<Map<Int, List<Item>>, DataError>> = resourceList
}

val resourceList = sequenceOf(Result.Loading, Result.Success(data = mapOf(1 to listOf(Item(listId = 1, name = "sample 0", id = 1), Item(listId = 1, name = "sample 1", id = 2), Item(listId = 1, name = "sample 2", id = 0)))), Result.Error(error = DataError.NetworkError.NO_INTERNET))