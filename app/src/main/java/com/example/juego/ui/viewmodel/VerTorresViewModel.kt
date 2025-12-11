package com.example.juego.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.juego.ui.model.Torre
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VerTorresViewModel(application: Application) : AndroidViewModel(application) {

    private val _towers = MutableStateFlow<List<Torre>>(emptyList())
    val towers: StateFlow<List<Torre>> = _towers

    private val crearTorreViewModel = CrearTorreViewModel(application)

    init {
        loadTowers()
    }

    private fun loadTowers() {
        viewModelScope.launch {
            _towers.value = crearTorreViewModel.getTowers()
        }
    }
}
