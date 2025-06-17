package com.harismexis.listdetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.harismexis.listdetail.ui.theme.NasaApisAppTheme
import com.harismexis.listdetail.viewmodel.MainVm
import com.harismexis.listdetail.screens.MAIN_SCREEN
import com.harismexis.listdetail.screens.MainScreen
import com.harismexis.listdetail.screens.PREF_SCREEN
import com.harismexis.listdetail.screens.PrefScreen
import com.harismexis.listdetail.screens.SmallTopAppBar

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NasaApisAppTheme {
                App()
            }
        }
    }

    @Composable
    private fun App(
        navController: NavHostController = rememberNavController(),
        mainVm: MainVm = viewModel(),
    ) {
        val backStackEntry = navController.currentBackStackEntryAsState()
        val isHomeScreen = backStackEntry.value?.destination?.route != MAIN_SCREEN

        Scaffold(
            topBar = {
                SmallTopAppBar(
                    onDateSelected = { date ->
                        mainVm.updateData(date)
                    },
                    onSettingsClicked = {
                        navController.navigate(route = PREF_SCREEN)
                    },
                    canNavigateBack = isHomeScreen,
                    navigateUp = {
                        navController.navigateUp()
                    },
                )
            },
        ) { padding ->
            NavHostBuilder(navController, mainVm, padding)
        }
    }
}

@Composable
private fun NavHostBuilder(
    navController: NavHostController,
    mainVm: MainVm,
    padding: PaddingValues,
) {
    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState()),
        navController = navController,
        startDestination = MAIN_SCREEN,
    ) {
        composable(route = MAIN_SCREEN) {
            MainScreen(mainVm)
        }
        composable(route = PREF_SCREEN) {
            PrefScreen()
        }
    }
}
