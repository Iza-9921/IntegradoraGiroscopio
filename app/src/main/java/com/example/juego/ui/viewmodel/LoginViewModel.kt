package com.example.juego.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juego.ui.state.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onLoginChange(user: String, pass: String) {
        _uiState.update { currentState ->
            currentState.copy(
                user = user,
                password = pass
            )
        }
    }

    fun onLoginClicked(onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            // Reset error before attempting
            _uiState.update { it.copy(showError = false) }

            if (_uiState.value.user == "admin" && _uiState.value.password == "123") {
                // Navigate on success
                onLoginSuccess()
            } else {
                // Show error
                _uiState.update { it.copy(showError = true) }
            }
        }
    }
}
