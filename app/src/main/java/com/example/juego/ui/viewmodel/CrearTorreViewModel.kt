package com.example.juego.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import com.example.juego.data.model.UserManager
import com.example.juego.ui.model.ColorAdapter
import com.example.juego.ui.model.Torre
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class CrearTorreViewModel(application: Application) : AndroidViewModel(application) {

    private val gson = GsonBuilder()
        .registerTypeAdapter(Color::class.java, ColorAdapter())
        .create()

    fun saveTower(name: String, color: Color) {
        val currentUser = UserManager.currentUser.value ?: return
        val userId = currentUser.id ?: return

        val sharedPref = getApplication<Application>().getSharedPreferences("GamePrefs", Context.MODE_PRIVATE) ?: return
        val json = sharedPref.getString("towers_by_user", null)
        val type = object : TypeToken<MutableMap<Int, MutableList<Torre>>>() {}.type
        val allTowers: MutableMap<Int, MutableList<Torre>> = gson.fromJson(json, type) ?: mutableMapOf()

        val userTowers = allTowers.getOrPut(userId) { mutableListOf() }
        userTowers.add(Torre(name, color))

        sharedPref.edit {
            putString("towers_by_user", gson.toJson(allTowers))
        }
    }

    fun getTowers(): List<Torre> {
        val currentUser = UserManager.currentUser.value ?: return emptyList()
        val userId = currentUser.id ?: return emptyList()

        val sharedPref = getApplication<Application>().getSharedPreferences("GamePrefs", Context.MODE_PRIVATE) ?: return emptyList()
        val json = sharedPref.getString("towers_by_user", null)
        val type = object : TypeToken<Map<Int, List<Torre>>>() {}.type
        val allTowers: Map<Int, List<Torre>> = gson.fromJson(json, type) ?: emptyMap()

        return allTowers[userId] ?: emptyList()
    }

    fun deleteTower(torre: Torre) {
        val currentUser = UserManager.currentUser.value ?: return
        val userId = currentUser.id ?: return

        val sharedPref = getApplication<Application>().getSharedPreferences("GamePrefs", Context.MODE_PRIVATE) ?: return
        val json = sharedPref.getString("towers_by_user", null)
        val type = object : TypeToken<MutableMap<Int, MutableList<Torre>>>() {}.type
        val allTowers: MutableMap<Int, MutableList<Torre>> = gson.fromJson(json, type) ?: return

        val userTowers = allTowers[userId] ?: return
        userTowers.remove(torre)

        sharedPref.edit {
            putString("towers_by_user", gson.toJson(allTowers))
        }
    }

    // Esta es la nueva función para actualizar una torre.
    fun updateTower(originalTowerName: String, newName: String, newColor: Color) {
        val currentUser = UserManager.currentUser.value ?: return
        val userId = currentUser.id ?: return

        val sharedPref = getApplication<Application>().getSharedPreferences("GamePrefs", Context.MODE_PRIVATE) ?: return
        val json = sharedPref.getString("towers_by_user", null)
        val type = object : TypeToken<MutableMap<Int, MutableList<Torre>>>() {}.type
        val allTowers: MutableMap<Int, MutableList<Torre>> = gson.fromJson(json, type) ?: return

        val userTowers = allTowers[userId] ?: return
        // Buscamos la torre por su nombre original.
        val towerIndex = userTowers.indexOfFirst { it.name == originalTowerName }
        if (towerIndex != -1) {
            // Y la actualizamos con los nuevos datos, manteniendo su puntuación.
            val updatedTower = userTowers[towerIndex].copy(name = newName, color = newColor)
            userTowers[towerIndex] = updatedTower
        }

        sharedPref.edit {
            putString("towers_by_user", gson.toJson(allTowers))
        }
    }
}
