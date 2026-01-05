package com.harismexis.listdetail.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.harismexis.listdetail.api.Character
import com.harismexis.listdetail.api.getValueOrNa
import com.harismexis.listdetail.viewmodel.ListVm

const val LIST_SCREEN = "ListScreen"

@Composable
fun ListScreen(
    listVm: ListVm,
    onItemClick: (Character) -> Unit = {},
) {
    val listState: LazyListState = rememberLazyListState()
    val isLoading: Boolean = listVm.isLoading.collectAsStateWithLifecycle().value
    val items: List<Character> = listVm.models.collectAsStateWithLifecycle().value ?: emptyList()

    LaunchedEffect(Unit) {
        listVm.updateData()
    }

    if (isLoading) {
        LoadingView()
    } else {
        ListView(items, listState, onItemClick)
    }
}

@Composable
fun ListView(
    items: List<Character>,
    listState: LazyListState,
    onItemClick: (Character) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(all = 8.dp),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            count = items.size,
            contentType = { index -> items[index] },
            itemContent = { index ->
                ItemRow(items[index], onItemClick)
            }
        )
    }
}

@Composable
private fun ItemRow(
    item: Character,
    onItemClick: (Character) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(true) {
                onItemClick(item)
            }
            .border(width = 1.dp, color = Color.Black)
            .padding(start = 10.dp, top = 8.dp, bottom = 8.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(text = item.name.getValueOrNa())
            Text(text = item.status.getValueOrNa())
            Text(text = item.species.getValueOrNa())
        }
        Spacer(modifier = Modifier.weight(1f))
        Column {
            SubcomposeAsyncImage(
                modifier = Modifier.size(100.dp, 100.dp),
                model = item.image,
                contentDescription = "Translated description of what the image contains",
                loading = { LoadingView() },
                // error = { ImageErrorView() },
            )
        }
    }
}

@Composable
private fun LoadingView() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}