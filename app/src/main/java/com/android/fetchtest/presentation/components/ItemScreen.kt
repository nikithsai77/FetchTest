package com.android.fetchtest.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.android.fetchtest.domain.util.DataError
import com.android.fetchtest.domain.model.FetchItem
import com.android.fetchtest.domain.util.Result
import com.android.fetchtest.presentation.theme.FetchTestTheme

@Composable
fun ItemScreen(
    modifier: Modifier = Modifier,
    resource: Result<Map<Int, List<FetchItem>>, DataError>,
    onRetry: () -> Unit
) {
    when(resource) {
        is Result.Loading -> LoadingSymbol(modifier)
        is Result.Success -> DisplayItems(modifier, itemList = resource.data)
        is Result.Error -> Retry(modifier, errorMsg = resource.error.getErrorDescription(), onRetry = onRetry)
    }
}

@Composable
fun LoadingSymbol(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.padding(all = 4.dp))
        Text(text = stringResource(id = R.string.please_wait_loading), modifier = Modifier.testTag(tag = TestTags.LOADING))
    }
}

@Composable
fun Retry(modifier: Modifier = Modifier, errorMsg: String, onRetry: () -> Unit) {
    Column(modifier = modifier.fillMaxSize().testTag(tag = TestTags.RETRY), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = errorMsg , textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge)
        Button(onClick = { onRetry.invoke() }) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
fun DisplayItems(modifier: Modifier = Modifier, itemList: Map<Int, List<FetchItem>>) {
    LazyColumn(modifier = modifier.fillMaxWidth().testTag(tag = TestTags.SUCCESS)) {
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
        .padding(all = 8.dp))
}

@Composable
fun ItemRow(item: FetchItem) {
    Column(modifier = Modifier.padding(all = 8.dp)) {
        Text(text = "ID: ${item.id}", style = MaterialTheme.typography.bodySmall, overflow = TextOverflow.Ellipsis)
        Text(text = "Name: ${item.name}", style = MaterialTheme.typography.bodyMedium, overflow = TextOverflow.Ellipsis)
    }
}

@PreviewLightDark
@Composable
fun MPreview() {
    FetchTestTheme {
        DisplayItems(
            itemList = mapOf(
                1 to listOf(
                    FetchItem(listId = 1, name = "sample 0", id = 1),
                    FetchItem(listId = 1, name = "sample 1", id = 2),
                    FetchItem(listId = 1, name = "sample 2", id = 0)
                ),
                2 to listOf(
                    FetchItem(listId = 2, name = "sample 0", id = 2),
                    FetchItem(listId = 2, name = "sample 1", id = 2),
                    FetchItem(listId = 2, name = "sample 2", id = 2)
                )
            )
        )
    }
}
