package com.example.juego.ui.viewmodel

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.juego.ui.model.Torre
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Este ViewModel prepara los datos para la pantalla "Editar Torre".
class EditarTorreViewModel(application: Application, private val towerName: String) : AndroidViewModel(application) {

    // Aquí guardamos los datos de la torre que estamos editando.
    private val _torre = MutableStateFlow<Torre?>(null)
    val torre: StateFlow<Torre?> = _torre

    private val crearTorreViewModel = CrearTorreViewModel(application)

    init {
        loadTower()
    }

    // Esta función carga los datos de la torre específica que se va a editar.
    private fun loadTower() {
        viewModelScope.launch {
            val towers = crearTorreViewModel.getTowers()
            _torre.value = towers.find { it.name == towerName }
        }
    }

    // Esta función llama a la lógica para actualizar la torre con los nuevos datos.
    fun updateTower(newName: String, newColor: Color) {
        viewModelScope.launch {
            crearTorreViewModel.updateTower(towerName, newName, newColor)
        }
    }
}
