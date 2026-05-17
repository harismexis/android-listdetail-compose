package com.harismexis.listdetail.ui.screens

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.harismexis.listdetail.ui.theme.ListDetailAppTheme
import com.harismexis.listdetail.ui.viewmodel.DetailVm
import com.harismexis.listdetail.ui.viewmodel.ListVm

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.Companion.dark(Color.TRANSPARENT))
        setContent {
            ListDetailAppTheme {
                App()
            }
        }
    }

    @Composable
    private fun App(
        navController: NavHostController = rememberNavController(),
        listVm: ListVm = viewModel(),
        detailVm: DetailVm = viewModel(),
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val isHomeScreen = backStackEntry?.destination?.route == LIST_SCREEN

        Scaffold(
            topBar = {
                SmallTopAppBar(
                    canNavigateBack = !isHomeScreen,
                    navigateUp = {
                        navController.navigateUp()
                    },
                )
            },
        ) { padding ->
            NavHost(
                modifier = Modifier.Companion
                    .fillMaxSize()
                    .padding(padding),
                navController = navController,
                startDestination = LIST_SCREEN,
            ) {
                composable(route = LIST_SCREEN) {
                    ListScreen(listVm, onItemClick = { item ->
                        detailVm.setItem(item)
                        navController.navigate(route = DETAIL_SCREEN)
                    })
                }
                composable(route = DETAIL_SCREEN) {
                    ItemScreen(detailVm)
                }
            }
        }
    }
}