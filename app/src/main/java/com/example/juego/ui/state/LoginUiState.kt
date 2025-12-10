package com.example.juego.ui.state

data class LoginUiState(
    val user: String = "",
    val password: String = "",
    val showError: Boolean = false
)
