package com.example.juego.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.juego.R
import com.example.juego.ui.components.buttons.PrimaryButton
import com.example.juego.ui.components.images.CircularImage
import com.example.juego.ui.components.texts.Title
import com.example.juego.ui.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel = viewModel()) {
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        CircularImage(R.drawable.block)
        Title("Registro")

        // Usuario
        TextField(
            value = viewModel.nombre.value,
            onValueChange = {
                viewModel.nombre.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Nombre") },
            singleLine = true
        )
        //apellido
        TextField(
            value = viewModel.apellido.value,
            onValueChange = {
                viewModel.apellido.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Apellido") },
            singleLine = true
        )
        // Correo
        TextField(
            value = viewModel.email.value,
            onValueChange = {
                viewModel.email.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Correo electronico") },
            placeholder = { Text("ejemplo@correo.com") },
            singleLine = true
        )

        TextField(
            value = viewModel.password.value,
            onValueChange = {
                viewModel.password.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Contraseña") },
            singleLine = true
        )

        TextField(
            value = viewModel.confirmPassword.value,
            onValueChange = {
                viewModel.confirmPassword.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Confirmar contraseña") },
            singleLine = true
        )

        if (viewModel.registerError.value.isNotEmpty()) {
            Text(
                text = viewModel.registerError.value,
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        
        if (viewModel.isLoading.value) {
            CircularProgressIndicator()
        }

        Spacer(modifier = Modifier.height(8.dp))

        PrimaryButton(
            text = "Registrarse",
            enabled = !viewModel.isLoading.value
        ) {
            viewModel.register {
                // Navegar de vuelta al login o mostrar éxito
                navController.popBackStack() 
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        PrimaryButton("Volver al Login") {
            navController.popBackStack()
        }
    }
}