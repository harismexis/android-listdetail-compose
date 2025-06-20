package com.harismexis.listdetail.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.harismexis.listdetail.api.getValueOrNa
import com.harismexis.listdetail.viewmodel.DetailVm

const val DETAIL_SCREEN = "DetailScreen"

@Composable
fun ItemScreen(detailVm: DetailVm) {
    val item = detailVm.item ?: return
    Column(
        modifier = Modifier
            .fillMaxSize(),
        // verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = item.image,
                contentDescription = "Translated description of what the image contains",
                loading = { ImageLoadingView() },
                // error = { ImageErrorView() },
            )
        }
        Text(text = item.name.getValueOrNa())
        Text(text = item.status.getValueOrNa())
        Text(text = item.species.getValueOrNa())
        Text(text = item.type.getValueOrNa())
        Text(text = item.gender.getValueOrNa())
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