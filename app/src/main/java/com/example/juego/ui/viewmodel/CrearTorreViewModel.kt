package com.example.juego.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.AndroidViewModel

class CrearTorreViewModel(application: Application) : AndroidViewModel(application) {

    fun saveTower(name: String, color: Color) {
        val sharedPref = getApplication<Application>().getSharedPreferences("GamePrefs", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("tower_name", name)
            putString("theme_color", String.format("#%06X", (0xFFFFFF and color.toArgb())))
            apply()
        }
    }
}
