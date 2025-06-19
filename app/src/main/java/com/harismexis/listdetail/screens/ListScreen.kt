package com.harismexis.listdetail.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.harismexis.listdetail.api.Character
import com.harismexis.listdetail.api.genderOrEmpty
import com.harismexis.listdetail.api.nameOrEmpty
import com.harismexis.listdetail.api.speciesOrEmpty
import com.harismexis.listdetail.api.statusOrEmpty
import com.harismexis.listdetail.api.typeOrEmpty
import com.harismexis.listdetail.viewmodel.ListVm

const val LIST_SCREEN = "ListScreen"

@Composable
fun ListScreen(listVm: ListVm) {
    val listState: LazyListState = rememberLazyListState()
    val isLoading: Boolean = listVm.isLoading.collectAsStateWithLifecycle().value
    val items: List<Character> = listVm.models.collectAsStateWithLifecycle().value ?: emptyList()

    LaunchedEffect(Unit) {
        listVm.updateData()
    }

    if (isLoading) {
        LoadingView()
    } else {
        ListView(items, listState)
    }
}

@Composable
fun ListView(
    items: List<Character>,
    listState: LazyListState,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        state = listState,
    ) {
        items(
            count = items.size,
            contentType = { index -> items[index] },
            itemContent = { index ->
                ItemRow(items[index])
            }
        )
    }
}

@Composable
private fun ItemRow(item: Character) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(

        ) {
            Text(text = item.nameOrEmpty())
            Text(text = item.statusOrEmpty())
            Text(text = item.speciesOrEmpty())
            Text(text = item.typeOrEmpty())
            Text(text = item.genderOrEmpty())
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(

        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.size(100.dp, 200.dp),
                model = item.image,
                contentDescription = "Translated description of what the image contains",
                loading = { ImageLoadingView() },
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

@Composable
private fun ImageLoadingView() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}