package com.example.juego.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.juego.ui.screens.GameMenuScreen
import com.example.juego.ui.screens.GameScreen
import com.example.juego.ui.screens.LoginScreen
import com.example.juego.ui.screens.MenuScreen
import com.example.juego.ui.screens.RegisterScreen
import com.example.juego.ui.screens.TiendaScreen
import com.example.juego.ui.viewmodel.LoginViewModel
import com.example.juego.ui.viewmodel.TiendaViewModel

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

        composable(AppScreens.GameMenuScreen.route) {
            GameMenuScreen(navController = navController)
        }

        composable(AppScreens.GamePlayScreen.route) {
            GameScreen(navController = navController)
        }

        composable(AppScreens.TiendaScreen.route) {
            val viewModel: TiendaViewModel = viewModel()
            TiendaScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(AppScreens.ForgotPasswordScreen.route) {
            // TODO: Implement ForgotPasswordScreen
        }
    }
}