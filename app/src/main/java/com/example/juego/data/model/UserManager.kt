package com.example.juego.data.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Este objeto es como un guardián que vigila qué jugador ha iniciado sesión.
// Lo usamos para que toda la aplicación sepa quién es el usuario actual.
object UserManager {

    // Aquí guardamos los datos del jugador. Usamos StateFlow para que si el jugador cambia (o cierra sesión),
    // la aplicación se pueda enterar y reaccionar.
    private val _currentUser = MutableStateFlow<Jugador?>(null)
    val currentUser: StateFlow<Jugador?> = _currentUser

    // Cuando un jugador inicia sesión, llamamos a esta función para guardarlo.
    fun login(jugador: Jugador) {
        _currentUser.value = jugador
    }

    // Cuando el jugador cierra sesión, llamamos a esta función para borrar sus datos.
    fun logout() {
        _currentUser.value = null
    }
}
