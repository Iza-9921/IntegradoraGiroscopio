package com.example.juego.data.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object UserManager {

    // Aquí guardamos los datos del jugador
    private val _currentUser = MutableStateFlow<Jugador?>(null)
    val currentUser: StateFlow<Jugador?> = _currentUser

    // Cuando un jugador inicia sesin, llamamos a esta funcion para guardarlo.
    fun login(jugador: Jugador) {
        _currentUser.value = jugador
    }

    fun logout() {
        _currentUser.value = null
    }
}
