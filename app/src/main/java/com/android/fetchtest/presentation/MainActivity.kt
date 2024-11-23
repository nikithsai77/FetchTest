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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.fetchtest.common.ClickEvent
import com.android.fetchtest.common.Resource
import com.android.fetchtest.domain.Item
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
fun GreetingPreview(
    @PreviewParameter(ResourceProvide::class) resource: Resource<Map<Int, List<Item>>>
) {
    FetchTestTheme {
        ItemScreen(resource) {}
    }
}

class ResourceProvide: PreviewParameterProvider<Resource<Map<Int, List<Item>>>> {
    override val values: Sequence<Resource<Map<Int, List<Item>>>> = resourceList
}

val resourceList = sequenceOf(Resource.Loading, Resource.Success(itemList = mapOf(1 to listOf(Item(listId = 1, name = "sample 0", id = 1), Item(listId = 1, name = "sample 1", id = 2), Item(listId = 1, name = "sample 2", id = 0)))), Resource.Error(error = "SomeThingWent Wrong Try Again Later!"))