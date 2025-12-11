package com.example.juego.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.juego.ui.components.texts.Title
import com.example.juego.ui.theme.JuegoTheme

@Composable
fun EditUserScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        Title("Editar Usuario")
        
        Text("Pantalla en construcción")

        Button(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditUserScreen() {
    JuegoTheme {
        EditUserScreen(rememberNavController())
    }
}
