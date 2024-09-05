@file:OptIn(ExperimentalMaterial3Api::class)

package com.android.fetchtest.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.fetchtest.R
import com.android.fetchtest.common.UIState
import com.android.fetchtest.data.Item

@Composable
fun topAppBar() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(MaterialTheme.colorScheme.primary)
        .padding(16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Fetch", modifier = Modifier.weight(1f), fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onPrimary)
    }
}

@Composable
fun ItemScreen(uiState: UIState, retry: () -> Unit) {
    when(uiState) {
        is UIState.Loading -> LoadingSymbol()
        is UIState.Error -> Retry(uiState.error, retry)
        is UIState.Success -> DisplayItems(uiState.itemList)
    }
}

@Composable
fun LoadingSymbol() {
    Scaffold(topBar = { topAppBar() }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = stringResource(R.string.please_wait_loading))
        }
    }
}

@Composable
fun Retry(errorMsg: String, retry: () -> Unit) {
    Scaffold(topBar = { topAppBar() }) {
          Column(modifier = Modifier
              .fillMaxSize()
              .padding(it), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
             Text(text = errorMsg , style = MaterialTheme.typography.bodyLarge)
             Button(onClick = { retry.invoke() }) {
                 Text(text = stringResource(R.string.retry))
             }
        }
    }
}

@Composable
fun DisplayItems(itemList: Map<Int, List<Item>>) {
    Scaffold(topBar = { topAppBar() }) {
        LazyColumn(contentPadding = it, modifier = Modifier.fillMaxWidth()) {
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
}

@Composable
fun Title(listId: Int) {
    Text(text = "List ID : $listId", style = MaterialTheme.typography.bodyLarge, modifier = Modifier
        .fillMaxWidth()
        .background(Color.Red)
        .padding(8.dp))
}

@Composable
fun ItemRow(item: Item) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "ID: ${item.id}", style = MaterialTheme.typography.bodySmall)
        Text(text = "Name: ${item.name}", style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun mPreview() {
    DisplayItems(itemList = mapOf(1 to listOf(Item(listId = 1, name = "sample 0", id = 1), Item(listId = 1, name = "sample 1", id = 2), Item(listId = 1, name = "sample 2", id = 0)),
                                  2 to listOf(Item(listId = 2, name = "sample 0", id = 2), Item(listId = 2, name = "sample 1", id = 2), Item(listId = 2, name = "sample 2", id = 2))))
}