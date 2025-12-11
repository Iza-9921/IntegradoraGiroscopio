package com.example.juego.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.juego.ui.screens.CrearTorreScreen
import com.example.juego.ui.screens.EliminarTorreScreen
import com.example.juego.ui.screens.GamePlayScreen
import com.example.juego.ui.screens.LoginScreen
import com.example.juego.ui.screens.MenuScreen
import com.example.juego.ui.screens.RegisterScreen
import com.example.juego.ui.screens.SeleccionarTorreScreen
import com.example.juego.ui.screens.VerTorresScreen
import com.example.juego.ui.viewmodel.LoginViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.LoginScreen.route
    ) {
        composable(AppScreens.LoginScreen.route) {
            val viewModel: LoginViewModel = viewModel()
            LoginScreen(viewModel = viewModel, navController = navController)
        }

        composable(AppScreens.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }

        composable(AppScreens.MenuScreen.route) {
            MenuScreen(navController = navController)
        }

        composable(
            route = AppScreens.GamePlayScreen.route,
            arguments = listOf(navArgument("towerName") { type = NavType.StringType })
        ) { backStackEntry ->
            val towerName = backStackEntry.arguments?.getString("towerName")
            GamePlayScreen(navController = navController, towerName = towerName)
        }

        composable(AppScreens.CrearTorreScreen.route) {
            CrearTorreScreen(navController = navController)
        }

        composable(AppScreens.VerTorresScreen.route) {
            VerTorresScreen(navController = navController)
        }

        composable(AppScreens.SeleccionarTorreScreen.route) {
            SeleccionarTorreScreen(navController = navController)
        }

        composable(AppScreens.EliminarTorreScreen.route) {
            EliminarTorreScreen(navController = navController)
        }

        composable(AppScreens.ForgotPasswordScreen.route) {
            // TODO: Implement ForgotPasswordScreen
        }
    }
}
