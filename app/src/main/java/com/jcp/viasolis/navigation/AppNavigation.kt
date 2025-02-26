package com.jcp.viasolis.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jcp.viasolis.ui.screens.HomeScreen
import com.jcp.viasolis.ui.screens.TrailsScreen
import com.jcp.viasolis.ui.screens.TrailDetailsScreen
import com.jcp.viasolis.ui.screens.NavigationScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("trails") { TrailsScreen(navController) }
        composable("trail_details") { TrailDetailsScreen(navController) }
        composable("navigation") { NavigationScreen(navController) }
    }
}