package com.example.juego.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TiendaViewModel(application: Application) : AndroidViewModel(application) {
    private val _availableThemes = MutableStateFlow<List<Tema>>(emptyList())
    val availableThemes: StateFlow<List<Tema>> = _availableThemes

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage

    init {
        // Temas predefinidos para la tienda
        _availableThemes.value = listOf(
            Tema("wood", "Madera", "#8B4513"),
            Tema("ice", "Hielo", "#ADD8E6"),
            Tema("scifi", "Sci-Fi", "#00FF00"),
            Tema("stone", "Piedra", "#808080"),
            Tema("gold", "Dorado", "#FFD700")
        )
    }

    fun updatePlayerTheme(playerId: String, themeId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Guardar el color en SharedPreferences
                val theme = _availableThemes.value.find { it.id == themeId }
                if (theme != null) {
                    val sharedPref = getApplication<Application>().getSharedPreferences("GamePrefs", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("theme_color", theme.colorHex)
                        apply()
                    }
                    _statusMessage.value = "Tema actualizado: ${theme.nombre}"
                } else {
                    _statusMessage.value = "Error: Tema no encontrado"
                }

            } catch (e: Exception) {
                _statusMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearStatusMessage() {
        _statusMessage.value = null
    }
}

// Clase de modelo para los temas
data class Tema(
    val id: String,
    val nombre: String,
    val colorHex: String
)
