package com.android.fetchtest.presentation.mainActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.android.fetchtest.R
import com.android.fetchtest.data.Item
import com.android.fetchtest.domain.DataError
import com.android.fetchtest.domain.Result
import com.android.fetchtest.presentation.getErrorDescription
import com.android.fetchtest.presentation.tag.TestTags
import com.android.fetchtest.ui.theme.FetchTestTheme

@Composable
fun ItemScreen(
    paddingValue: PaddingValues,
    resource: Result<Map<Int, List<Item>>, DataError>,
    clickEvent: () -> Unit
) {
    when(resource) {
        is Result.Loading -> LoadingSymbol(paddingValue)
        is Result.Success -> DisplayItems(paddingValue, resource.data)
        is Result.Error -> Retry(paddingValue, resource.error.getErrorDescription(), clickEvent)
    }
}

@Composable
fun LoadingSymbol(paddingValue: PaddingValues) {
    Column(modifier = Modifier.fillMaxSize().padding(paddingValue), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = stringResource(R.string.please_wait_loading), Modifier.testTag(tag = TestTags.LOADING))
    }
}

@Composable
fun Retry(paddingValue: PaddingValues, errorMsg: String, retry: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(paddingValue).testTag(tag = TestTags.RETRY), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = errorMsg , textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge)
        Button(onClick = { retry.invoke() }) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Composable
fun DisplayItems(paddingValue: PaddingValues, itemList: Map<Int, List<Item>>) {
    LazyColumn(contentPadding = paddingValue, modifier = Modifier.fillMaxWidth().testTag(tag = TestTags.SUCCESS)) {
        itemList.forEach { (listId, items) ->
            item {
                Title(listId)
            }
            items(items) { item ->
                ItemRow(item)
            }
        }
    }
}

@Composable
fun Title(listId: Int) {
    Text(text = "List ID : $listId", style = MaterialTheme.typography.bodyLarge, overflow = TextOverflow.Ellipsis, modifier = Modifier
        .fillMaxWidth()
        .background(Color.Red)
        .padding(8.dp))
}

@Composable
fun ItemRow(item: Item) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "ID: ${item.id}", style = MaterialTheme.typography.bodySmall, overflow = TextOverflow.Ellipsis)
        Text(text = "Name: ${item.name}", style = MaterialTheme.typography.bodyMedium, overflow = TextOverflow.Ellipsis)
    }
}

@PreviewLightDark
@Composable
fun MPreview() {
    FetchTestTheme {
        DisplayItems(
            paddingValue = PaddingValues(10.dp),
            itemList = mapOf(
                1 to listOf(
                    Item(listId = 1, name = "sample 0", id = 1),
                    Item(listId = 1, name = "sample 1", id = 2),
                    Item(listId = 1, name = "sample 2", id = 0)
                ),
                2 to listOf(
                    Item(listId = 2, name = "sample 0", id = 2),
                    Item(listId = 2, name = "sample 1", id = 2),
                    Item(listId = 2, name = "sample 2", id = 2)
                )
            )
        )
    }
}