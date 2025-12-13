package com.example.juego.ui.viewmodel

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.juego.data.model.UserManager
import com.example.juego.data.network.RetrofitClient
import com.example.juego.ui.model.Torre
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditarTorreViewModel(application: Application, private val towerName: String)
    : AndroidViewModel(application) {
    private val _torre = MutableStateFlow<Torre?>(null)
    val torre: StateFlow<Torre?> = _torre
    private val api = RetrofitClient.instance
    init {
        loadTower()
    }

    private fun loadTower() {
        viewModelScope.launch {

            // obtiene el usuario actual si no existe se detiene
            val currentUser = UserManager.currentUser.value ?: return@launch
            // obtiene el ID del usuario si es nulo se detiene
            val userId = currentUser.id ?: return@launch

            try {
                // Llama a la API para obtener las torres del jugador
                val response = api.getTowersByPlayer(userId)

                if (response.isSuccessful) {
                    //torres o una lista vacia
                    val towers = response.body() ?: emptyList()

                    //busca la torre cuyo nombre coincide con el que queremos editar
                    _torre.value = towers.find { it.name == towerName }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

   //actualiza la torre
    fun updateTower(newName: String, newColor: Color, onSuccess: () -> Unit) {
        viewModelScope.launch {

            // busca la torre actual si no existe no continua.
            val currentTower = _torre.value ?: return@launch

            // busca el ID de la torre si no existe tampoco continua.
            val towerId = currentTower.id ?: return@launch

            // crea la torre con los valores actualizados
            val updatedTower = currentTower.copy(
                name = newName,
                color = newColor
            )

            try {
                // Llama a la API para actualizar la torre
                val response = api.updateTower(towerId, updatedTower)

                if (response.isSuccessful) {
                    // Si fue exitoso tenemos muestra torre actualizada
                    onSuccess()
                } else {
                    // En caso de error muestra mensaje
                    println("Error al actualizar la torre: ${response.code()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
