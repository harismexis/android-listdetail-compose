package com.harismexis.listdetail.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource

import com.harismexis.listdetail.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopAppBar(
    canNavigateBack: Boolean = false,
    navigateUp: () -> Unit = {},
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            navigationIconContentColor = Color.LightGray,
            actionIconContentColor = Color.LightGray,
            containerColor = colorResource(R.color.black_1),
            titleContentColor = Color.LightGray,
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = { navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigates back"
                    )
                }
            }
        },
        title = {
            Text("Rick & Morty")
        },
    )
}