package com.example.juego.ui.navigation

sealed class AppScreens(val route: String) {
    object LoginScreen : AppScreens("login")
    object RegisterScreen : AppScreens("register")
    object MenuScreen : AppScreens("menu")
    object GamePlayScreen : AppScreens("gameplay/{towerName}")
    object CrearTorreScreen : AppScreens("crear_torre")
    object VerTorresScreen : AppScreens("ver_torres")
    object SeleccionarTorreScreen : AppScreens("seleccionar_torre")
    object EliminarTorreScreen : AppScreens("eliminar_torre")
    object ForgotPasswordScreen : AppScreens("forgot_password")
}
