package com.example.juego.ui.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.juego.ui.components.buttons.PrimaryButton
import com.example.juego.ui.components.texts.Title
import com.example.juego.ui.viewmodel.CrearTorreViewModel

@Composable
fun CrearTorreScreen(navController: NavController) {
    val viewModel: CrearTorreViewModel = viewModel()
    var towerName by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf<Color?>(null) }

    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Magenta)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Title("Crear Nueva Torre")

        TextField(
            value = towerName,
            onValueChange = { towerName = it },
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

        PrimaryButton("Guardar Torre") {
            if (towerName.isNotBlank() && selectedColor != null) {
                viewModel.saveTower(towerName, selectedColor!!)
                navController.popBackStack() // Go back to the menu
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        PrimaryButton("Volver") {
            navController.popBackStack()
        }
    }
}
