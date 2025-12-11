package com.example.juego.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.juego.ui.navigation.AppScreens
import com.example.juego.ui.theme.JuegoTheme

@Composable
fun MenuScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        Title("Torre de Equilibrio")

        Button(
            onClick = { navController.navigate(AppScreens.SeleccionarTorreScreen.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Juego")
        }

        Button(
            onClick = { navController.navigate(AppScreens.VerTorresScreen.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Torres")
        }

        Button(
            onClick = { navController.navigate(AppScreens.CrearTorreScreen.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear Torre")
        }

        Button(
            onClick = { navController.navigate(AppScreens.EliminarTorreScreen.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Eliminar Torre")
        }

        Button(
            onClick = {
                navController.navigate(AppScreens.LoginScreen.route) {
                    popUpTo(0)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar Sesión")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMenuScreen() {
    JuegoTheme {
        val navController = rememberNavController()
        MenuScreen(navController = navController)
    }
}
