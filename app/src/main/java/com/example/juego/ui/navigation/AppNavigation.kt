package com.example.juego.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.juego.ui.screens.LoginScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route) {
        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(navController)
        }
        // TODO: Add other screens here later
        // composable(route = AppScreens.GameScreen.route) { GameScreen(navController) }
        // composable(route = AppScreens.RankingScreen.route) { RankingScreen(navController) }
    }
}
