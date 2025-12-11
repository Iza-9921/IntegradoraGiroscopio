package com.example.juego.ui.screens

import android.Manifest
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.juego.ui.navigation.AppScreens
import com.example.juego.ui.theme.JuegoTheme
import com.example.juego.ui.viewmodel.GameViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun GameMenuScreen(navController: NavController) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { navController.navigate(AppScreens.GamePlayScreen.route) }) {
                Text("Iniciar Juego")
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GameScreen(navController: NavController) {
    val gameViewModel: GameViewModel = viewModel()
    val sensorPermissionState = rememberPermissionState(Manifest.permission.BODY_SENSORS)
    val platformOffset by gameViewModel.platformOffset.collectAsState()
    val ballPosition by gameViewModel.ballPosition.collectAsState()
    val score by gameViewModel.score.collectAsState()
    val isGameOver by gameViewModel.isGameOver.collectAsState()
    val platformColor by gameViewModel.platformColor.collectAsState()
    val towerName by gameViewModel.towerName.collectAsState()

    LaunchedEffect(Unit) {
        sensorPermissionState.launchPermissionRequest()
        gameViewModel.refreshTowerData()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            gameViewModel.setScreenSize(size.width, size.height)
            val platformWidth = 300f
            val platformHeight = 50f
            val platformY = size.height - platformHeight

            // Dibuja la plataforma
            drawRect(
                color = platformColor,
                topLeft = Offset(x = (size.width - platformWidth) / 2 + platformOffset.x, y = platformY),
                size = androidx.compose.ui.geometry.Size(platformWidth, platformHeight)
            )

            // Dibuja la bola
            drawCircle(
                color = Color.Red,
                radius = 20f,
                center = ballPosition
            )
        }

        if (isGameOver) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Game Over", color = Color.Black)
                Text(text = "Puntuación: $score", color = Color.Black)
                Button(onClick = { gameViewModel.restartGame() }) {
                    Text("Reiniciar")
                }
            }
        } else {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = towerName, color = Color.Black)
                Text(text = "Puntuación: $score", color = Color.Black)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameMenuScreenPreview() {
    JuegoTheme {
        GameMenuScreen(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun GamePlayScreenPreview() {
    JuegoTheme {
        GameScreen(rememberNavController())
    }
}
