package com.example.juego.ui.screens

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.juego.ui.components.buttons.PrimaryButton
import com.example.juego.ui.components.texts.Title
import com.example.juego.ui.viewmodel.EditarTorreViewModel
import com.example.juego.ui.viewmodel.EditarTorreViewModelFactory

// Esta pantalla permite al jugador editar una torre que ya ha creado.
@Composable
fun EditarTorreScreen(navController: NavController, towerName: String) {
    // Usamos la "fábrica" para crear el ViewModel, pasándole el nombre de la torre a editar.
    val application = LocalContext.current.applicationContext as Application
    val factory = EditarTorreViewModelFactory(application, towerName)
    val viewModel: EditarTorreViewModel = viewModel(factory = factory)

    val torre by viewModel.torre.collectAsState()

    // Estas variables guardan los nuevos datos (nombre y color) que el jugador elija.
    var newTowerName by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf<Color?>(null) }

    // Cuando la torre se carga, rellenamos los campos con sus datos actuales.
    LaunchedEffect(torre) {
        torre?.let {
            newTowerName = it.name
            selectedColor = it.color
        }
    }

    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Magenta)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Title("Editar Torre")

        TextField(
            value = newTowerName,
            onValueChange = { newTowerName = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Nombre de la Torre") },
            singleLine = true
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            colors.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color)
                        .clickable { selectedColor = color }
                        .border(
                            width = 2.dp,
                            color = if (selectedColor == color) Color.Black else Color.Transparent,
                            shape = CircleShape
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        PrimaryButton("Guardar Cambios") {
            if (newTowerName.isNotBlank() && selectedColor != null) {
                viewModel.updateTower(newTowerName, selectedColor!!)
                navController.popBackStack() // Volvemos a la lista de torres.
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        PrimaryButton("Volver") {
            navController.popBackStack()
        }
    }
}
