package com.example.juego.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.juego.ui.components.buttons.PrimaryButton
import com.example.juego.ui.components.texts.Title
import com.example.juego.ui.model.Torre
import com.example.juego.ui.viewmodel.VerTorresViewModel

@Composable
fun VerTorresScreen(navController: NavController) {
    val viewModel: VerTorresViewModel = viewModel()
    val towers by viewModel.towers.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title("Torres Creadas")

        if (towers.isEmpty()) {
            Text("Aún no has creado ninguna torre.")
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(towers) { tower ->
                    TorreItem(tower)
                }
            }
        }

        PrimaryButton("Volver") {
            navController.popBackStack()
        }
    }
}

@Composable
fun TorreItem(torre: Torre) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(torre.color)
                )
                Text(text = torre.name, modifier = Modifier.padding(start = 16.dp))
            }
            Text(text = "Puntuación: ${torre.puntuacion}")
        }
    }
}
