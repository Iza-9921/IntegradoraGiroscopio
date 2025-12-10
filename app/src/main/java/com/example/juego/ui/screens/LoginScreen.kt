package com.example.juego.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.juego.ui.navigation.AppScreens
import com.example.juego.ui.viewmodel.LoginViewModel

@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {

    val uiState by loginViewModel.uiState.collectAsState()

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = uiState.user,
                onValueChange = { user -> loginViewModel.onLoginChange(user, uiState.password) },
                label = { Text("Usuario") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.password,
                onValueChange = { pass -> loginViewModel.onLoginChange(uiState.user, pass) },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation()
            )

            if (uiState.showError) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Usuario o contraseña incorrectos", color = Color.Red)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                loginViewModel.onLoginClicked {
                    // On Success
                    navController.navigate(AppScreens.GameScreen.route) {
                        // Avoid stacking login screen
                        popUpTo(AppScreens.LoginScreen.route) { inclusive = true }
                    }
                }
            }) {
                Text("Entrar")
            }
        }
    }
}
