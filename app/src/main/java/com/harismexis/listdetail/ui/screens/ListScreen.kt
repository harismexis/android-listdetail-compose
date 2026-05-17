package com.harismexis.listdetail.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.harismexis.listdetail.data.api.Character
import com.harismexis.listdetail.data.api.getValueOrNa
import com.harismexis.listdetail.ui.viewmodel.ListVm

const val LIST_SCREEN = "ListScreen"

@Composable
fun ListScreen(
    listVm: ListVm,
    onItemClick: (Character) -> Unit = {},
) {
    val listState: LazyListState = rememberLazyListState()
    val items: LazyPagingItems<Character> = listVm.characters.collectAsLazyPagingItems()

    val isInitialLoading = items.loadState.refresh is LoadState.Loading && items.itemCount == 0
    if (isInitialLoading) {
        LoadingView()
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(all = 8.dp),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                count = items.itemCount,
                key = { index -> items[index]?.id ?: index }
            ) { index ->
                val character = items[index]
                character?.let {
                    ItemRow(it, onItemClick)
                }
            }

            when (items.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        LoadingView()
                    }
                }

                is LoadState.Error -> {
                    val error = (items.loadState.append as LoadState.Error).error
                    item {
                        Text("Error: ${error.message}")
                    }
                }

                else -> Unit
            }
        }
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
            .clickable {
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
        SubcomposeAsyncImage(
            modifier = Modifier.size(100.dp, 100.dp),
            model = item.image,
            contentDescription = "Translated description of what the image contains",
            loading = { ImageLoadingView() },
            contentScale = ContentScale.Crop
            // error = { ImageErrorView() },
        )
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ImageLoadingView() {
    Box(
        modifier = Modifier.size(100.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
