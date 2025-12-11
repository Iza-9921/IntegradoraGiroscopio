package com.example.juego.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import com.example.juego.ui.model.ColorAdapter
import com.example.juego.ui.model.Torre
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class CrearTorreViewModel(application: Application) : AndroidViewModel(application) {

    private val gson = GsonBuilder()
        .registerTypeAdapter(Color::class.java, ColorAdapter())
        .create()

    fun saveTower(name: String, color: Color) {
        val sharedPref = getApplication<Application>().getSharedPreferences("GamePrefs", Context.MODE_PRIVATE) ?: return
        val json = sharedPref.getString("towers", null)
        val type = object : TypeToken<MutableList<Torre>>() {}.type
        val towers: MutableList<Torre> = gson.fromJson(json, type) ?: mutableListOf()

        towers.add(Torre(name, color))

        sharedPref.edit {
            putString("towers", gson.toJson(towers))
        }
    }

    fun getTowers(): List<Torre> {
        val sharedPref = getApplication<Application>().getSharedPreferences("GamePrefs", Context.MODE_PRIVATE) ?: return emptyList()
        val json = sharedPref.getString("towers", null)
        val type = object : TypeToken<List<Torre>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    fun deleteTower(torre: Torre) {
        val sharedPref = getApplication<Application>().getSharedPreferences("GamePrefs", Context.MODE_PRIVATE) ?: return
        val json = sharedPref.getString("towers", null)
        val type = object : TypeToken<MutableList<Torre>>() {}.type
        val towers: MutableList<Torre> = gson.fromJson(json, type) ?: return

        towers.remove(torre)

        sharedPref.edit {
            putString("towers", gson.toJson(towers))
        }
    }
}
