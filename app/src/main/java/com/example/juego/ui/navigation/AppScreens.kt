package com.example.juego.ui.navigation

sealed class AppScreens(val route: String) {
    object LoginScreen : AppScreens("login_screen")
    object GameScreen : AppScreens("game_screen")
    object RankingScreen : AppScreens("ranking_screen")
}
