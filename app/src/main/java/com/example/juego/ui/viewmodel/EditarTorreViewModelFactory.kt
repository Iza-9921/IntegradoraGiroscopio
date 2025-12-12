package com.example.juego.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// Esta es una "fábrica" especial para crear nuestro EditarTorreViewModel.
// La necesitamos para pasarle el nombre de la torre que queremos editar.
class EditarTorreViewModelFactory(private val application: Application, private val towerName: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditarTorreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditarTorreViewModel(application, towerName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
