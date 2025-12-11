package com.example.juego.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juego.data.model.Jugador
import com.example.juego.data.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val repository = UserRepository()

    var nombre = mutableStateOf("")
    var apellido = mutableStateOf("")
    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var confirmPassword = mutableStateOf("")

    var registerError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    fun register(onSuccess: () -> Unit) {
        if (nombre.value.isBlank() || email.value.isBlank() || password.value.isBlank()) {
            registerError.value = "Todos los campos son obligatorios"
            return
        }

        if (password.value != confirmPassword.value) {
            registerError.value = "Las contraseñas no coinciden"
            return
        }

        isLoading.value = true
        registerError.value = ""

        viewModelScope.launch {
            // Nota: El modelo Jugador no tiene campo apellido, usaremos nombre como username
            val nuevoJugador = Jugador(
                username = nombre.value,
                password = password.value,
                email = email.value
            )

            val result = repository.register(nuevoJugador)
            isLoading.value = false

            if (result.isSuccess) {
                onSuccess()
            } else {
                registerError.value = "Error al registrar: ${result.exceptionOrNull()?.message}"
            }
        }
    }
}
