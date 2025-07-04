package com.harismexis.listdetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.harismexis.listdetail.screens.DETAIL_SCREEN
import com.harismexis.listdetail.screens.ItemScreen
import com.harismexis.listdetail.screens.LIST_SCREEN
import com.harismexis.listdetail.screens.ListScreen
import com.harismexis.listdetail.screens.PREF_SCREEN
import com.harismexis.listdetail.screens.PrefScreen
import com.harismexis.listdetail.screens.SmallTopAppBar
import com.harismexis.listdetail.ui.theme.ListDetailAppTheme
import com.harismexis.listdetail.viewmodel.DetailVm
import com.harismexis.listdetail.viewmodel.ListVm

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
        val backStackEntry = navController.currentBackStackEntryAsState()
        val isHomeScreen = backStackEntry.value?.destination?.route == LIST_SCREEN

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
            NavHostBuilder(navController, listVm, detailVm, padding)
        }
    }
}

@Composable
private fun NavHostBuilder(
    navController: NavHostController,
    listVm: ListVm,
    detailVm: DetailVm,
    padding: PaddingValues,
) {
    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        navController = navController,
        startDestination = LIST_SCREEN,
    ) {
        composable(route = LIST_SCREEN) {
            ListScreen(listVm, onItemClick = { item ->
                detailVm.item = item
                navController.navigate(route = DETAIL_SCREEN)
            })
        }
        composable(route = DETAIL_SCREEN) {
            ItemScreen(detailVm)
        }
        composable(route = PREF_SCREEN) {
            PrefScreen()
        }
    }
}
