package com.jcp.viasolis.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jcp.viasolis.ui.HikingViewModel
import com.jcp.viasolis.ui.screens.HomeScreen
import com.jcp.viasolis.ui.screens.TrailsScreen
import com.jcp.viasolis.ui.screens.TrailDetailsScreen
import com.jcp.viasolis.ui.screens.NavigationScreen

@Composable
fun AppNavigation() {
    val hikingViewModel: HikingViewModel = viewModel()

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController, hikingViewModel) }
        composable("trails") { TrailsScreen(navController, hikingViewModel) }
        composable(
            "trail_details/{trailId}",
            arguments = listOf(navArgument("trailId") { type = NavType.StringType })
        ) { backStackEntry ->
            val trailId = backStackEntry.arguments?.getString("trailId")
            TrailDetailsScreen(navController, trailId)
        }
        composable(
            "navigation/{trailId}",
            arguments = listOf(navArgument("trailId") { type = NavType.StringType })
        ) { backStackEntry ->
            val trailId = backStackEntry.arguments?.getString("trailId")
            NavigationScreen(navController, trailId)
        }
    }
}