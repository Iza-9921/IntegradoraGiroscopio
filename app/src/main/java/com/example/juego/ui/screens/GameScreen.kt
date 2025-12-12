package com.example.juego.ui.screens

import android.Manifest
import android.app.Application
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.juego.ui.theme.JuegoTheme
import com.example.juego.ui.viewmodel.GameViewModel
import com.example.juego.ui.viewmodel.GameViewModelFactory
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GamePlayScreen(navController: NavController, towerName: String?) {
    val application = LocalContext.current.applicationContext as Application
    val factory = GameViewModelFactory(application, towerName)
    val gameViewModel: GameViewModel = viewModel(factory = factory)

    val sensorPermissionState = rememberPermissionState(Manifest.permission.BODY_SENSORS)
    val platformOffset by gameViewModel.platformOffset.collectAsState()
    val ballPosition by gameViewModel.ballPosition.collectAsState()
    val score by gameViewModel.score.collectAsState()
    val isGameOver by gameViewModel.isGameOver.collectAsState()
    val platformColor by gameViewModel.platformColor.collectAsState()

    LaunchedEffect(Unit) {
        sensorPermissionState.launchPermissionRequest()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            gameViewModel.setScreenSize(size.width, size.height)
            val platformWidth = 300f
            val platformHeight = 50f
            val platformY = size.height - platformHeight

            drawRect(
                color = platformColor,
                topLeft = Offset(x = (size.width - platformWidth) / 2 + platformOffset.x, y = platformY),
                size = androidx.compose.ui.geometry.Size(platformWidth, platformHeight)
            )

            drawCircle(
                color = platformColor, // Use the platform's color for the ball
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
            Text(text = "Puntuación: $score", modifier = Modifier.padding(16.dp), color = Color.Black)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GamePlayScreenPreview() {
    JuegoTheme {
        GamePlayScreen(rememberNavController(), towerName = "PreviewTorre")
    }
}
