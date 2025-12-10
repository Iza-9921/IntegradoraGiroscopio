package com.example.juego.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.juego.ui.screens.LoginScreen
import com.example.juego.ui.screens.MenuScreen
import com.example.juego.ui.screens.RegisterScreen
import com.example.juego.ui.viewmodel.LoginViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            val vm: LoginViewModel = viewModel()
            LoginScreen(viewModel = vm, navController = navController)
        }
        composable("register") { RegisterScreen(navController) }
        composable("menu") { MenuScreen(navController) }


    }
}
