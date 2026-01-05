package com.harismexis.listdetail.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

import coil.compose.SubcomposeAsyncImage

import com.harismexis.listdetail.api.getValueOrNa
import com.harismexis.listdetail.viewmodel.DetailVm

const val DETAIL_SCREEN = "DetailScreen"

@Composable
fun ItemScreen(detailVm: DetailVm) {
    val item = detailVm.item ?: return
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            contentAlignment = Alignment.TopCenter,
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = item.image,
                contentDescription = "Translated description of what the image contains",
                loading = { ImageLoadingView() },
                // error = { ImageErrorView() },
            )
        }

        CustomText(text = item.name.getValueOrNa())
        CustomText(text = item.status.getValueOrNa())
        CustomText(text = item.species.getValueOrNa())
        CustomText(text = item.type.getValueOrNa())
        CustomText(text = item.gender.getValueOrNa())
    }
}

@Composable
private fun CustomText(
    text: String,
    paddingValues: PaddingValues = PaddingValues(start = 8.dp, end = 8.dp),
) {
    Text(
        text = text,
        modifier = Modifier.padding(paddingValues)
    )
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