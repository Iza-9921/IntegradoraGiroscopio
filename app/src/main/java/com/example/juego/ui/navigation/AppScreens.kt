package com.example.juego.ui.navigation

sealed class AppScreens(val route: String) {
    object LoginScreen : AppScreens("login")
    object RegisterScreen : AppScreens("register")
    object MenuScreen : AppScreens("menu")
    object GameMenuScreen : AppScreens("game")
    object GamePlayScreen : AppScreens("gameplay")
    object TiendaScreen : AppScreens("tienda")
    object CreateTowerScreen : AppScreens("create_tower")
    object ForgotPasswordScreen : AppScreens("forgot_password")
}