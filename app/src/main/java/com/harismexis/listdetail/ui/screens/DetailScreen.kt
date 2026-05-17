package com.harismexis.listdetail.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.harismexis.listdetail.data.api.getValueOrNa
import com.harismexis.listdetail.ui.viewmodel.DetailVm

const val DETAIL_SCREEN = "DetailScreen"

@Composable
fun ItemScreen(detailVm: DetailVm) {
    val item = detailVm.item
    if (item == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "No item found")
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = item.image,
                    contentDescription = "Translated description of what the image contains",
                    loading = { ImageLoadingView() },
                    // error = { ImageErrorView() },
                )
            }

            Spacer(
                modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth()
            )

            InfoText(text = "Name: ${item.name.getValueOrNa()}")
            InfoText(text = "Status: ${item.status.getValueOrNa()}")
            InfoText(text = "Species: ${item.species.getValueOrNa()}")
            InfoText(text = "Type: ${item.type.getValueOrNa()}")
            InfoText(text = "Gender: ${item.gender.getValueOrNa()}")
        }
    }
}

@Composable
private fun InfoText(
    text: String,
    padding: PaddingValues = PaddingValues(0.dp),
) {
    Text(
        text = text,
        modifier = Modifier.padding(padding)
    )
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
