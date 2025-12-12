package com.example.juego.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juego.data.model.Jugador
import com.example.juego.data.model.UserManager
import com.example.juego.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repository = UserRepository()

    var password = mutableStateOf("")
    var username = mutableStateOf("")
    var loginError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    fun login(onSuccess: (Jugador) -> Unit) {
        if (username.value.isBlank() || password.value.isBlank()) {
            loginError.value = "Por favor ingrese usuario y contraseña"
            return
        }

        isLoading.value = true
        loginError.value = ""

        viewModelScope.launch {
            val result = repository.login(username.value, password.value)
            isLoading.value = false
            
            if (result.isSuccess) {
                val jugador = result.getOrNull()
                if (jugador != null) {
                    // ¡Aquí está la magia! Guardamos el jugador que ha iniciado sesión.
                    UserManager.login(jugador)
                    onSuccess(jugador)
                }
            } else {
                loginError.value = "Error al iniciar sesión: ${result.exceptionOrNull()?.message}"
            }
        }
    }
}
