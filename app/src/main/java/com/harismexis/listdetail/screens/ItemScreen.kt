package com.harismexis.listdetail.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.harismexis.listdetail.api.genderOrEmpty
import com.harismexis.listdetail.api.nameOrEmpty
import com.harismexis.listdetail.api.speciesOrEmpty
import com.harismexis.listdetail.api.statusOrEmpty
import com.harismexis.listdetail.api.typeOrEmpty
import com.harismexis.listdetail.viewmodel.DetailVm

const val DETAIL_SCREEN = "DetailScreen"

@Composable
fun ItemScreen(detailVm: DetailVm) {
    val item = detailVm.item ?: return
    Column(
        modifier = Modifier
            //.fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = item.image,
            contentDescription = "Translated description of what the image contains",
            loading = { ImageLoadingView() },
            // error = { ImageErrorView() },
        )

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