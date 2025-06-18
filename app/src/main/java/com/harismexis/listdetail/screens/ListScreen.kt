package com.harismexis.listdetail.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.harismexis.listdetail.model.Model
import com.harismexis.listdetail.model.genderOrEmpty
import com.harismexis.listdetail.model.nameOrEmpty
import com.harismexis.listdetail.model.speciesOrEmpty
import com.harismexis.listdetail.model.statusOrEmpty
import com.harismexis.listdetail.model.typeOrEmpty

const val LIST_SCREEN = "ListScreen"

@Composable
fun ListScreen() {
    LoadingView()
}

@Composable
fun ListView(
    items: List<Model>,
    listState: LazyListState,
) {
    LazyColumn(state = listState) {
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
private fun ItemRow(item: Model) {
    Column {
        Text(text = item.nameOrEmpty())
        Text(text = item.statusOrEmpty())
        Text(text = item.speciesOrEmpty())
        Text(text = item.typeOrEmpty())
        Text(text = item.genderOrEmpty())
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