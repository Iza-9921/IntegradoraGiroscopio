package com.example.juego.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.juego.data.model.UserManager
import com.example.juego.data.network.RetrofitClient
import com.example.juego.ui.model.Torre
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VerTorresViewModel(application: Application) : AndroidViewModel(application) {

    // lista de torres del jugador
    private val _towers = MutableStateFlow<List<Torre>>(emptyList())
    val towers: StateFlow<List<Torre>> = _towers

    // cliente retrofit para acceder a la api
    private val api = RetrofitClient.instance

    // cargan las torres
    init {
        loadTowers()
    }

    // obtiene las torres del jugador actual desde el servidor
    private fun loadTowers() {
        viewModelScope.launch {
            val currentUser = UserManager.currentUser.value ?: return@launch
            val userId = currentUser.id ?: return@launch

            try {
                val response = api.getTowersByPlayer(userId)
                if (response.isSuccessful) {
                    _towers.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
