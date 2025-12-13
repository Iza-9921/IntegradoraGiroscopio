package com.example.juego.ui.viewmodel

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import com.example.juego.data.model.UserManager
import com.example.juego.data.network.RetrofitClient
import com.example.juego.ui.model.Torre

class CrearTorreViewModel(application: Application) : AndroidViewModel(application) {

    // Cliente de la API creado mediante Retrofit
    private val api = RetrofitClient.instance
    suspend fun saveTower(name: String, color: Color): Boolean {

        // Obtiene el usuario actual si no hay usuario, no hace nada
        val currentUser = UserManager.currentUser.value ?: return false

        // Obtiene el ID del usuario si es nulo tampoco hace nada
        val userId = currentUser.id ?: return false

        //crea la torre desde cero
        val newTower = Torre(
            playerId = userId,
            name = name,
            color = color,
            puntuacion = 0
        )

        return try {
            // Llama a la API para crear la torre y devuelve si fue exitoso
            val response = api.createTower(newTower)
            response.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false // Si hay un error, lo imprime y devuelve false
        }
    }
}
