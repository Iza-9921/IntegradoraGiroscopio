package com.example.juego.ui.viewmodel

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.juego.ui.model.ColorAdapter
import com.example.juego.ui.model.Torre
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel(application: Application, private val towerName: String?) : AndroidViewModel(application), SensorEventListener {

    private val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val gyroscope: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    private val _platformOffset = MutableStateFlow(Offset.Zero)
    val platformOffset: StateFlow<Offset> = _platformOffset

    private val _ballPosition = MutableStateFlow(Offset.Zero)
    val ballPosition: StateFlow<Offset> = _ballPosition

    private var ballVelocity = Offset(x = 5f, y = 5f)

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _isGameOver = MutableStateFlow(false)
    val isGameOver: StateFlow<Boolean> = _isGameOver

    private val _platformColor = MutableStateFlow(Color.Gray)
    val platformColor: StateFlow<Color> = _platformColor

    private var screenWidth = 0f
    private var screenHeight = 0f
    private var screenSizeSet = false

    private val gson = GsonBuilder()
        .registerTypeAdapter(Color::class.java, ColorAdapter())
        .create()

    init {
        loadTowerData()
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_GAME)
        viewModelScope.launch {
            while (true) {
                if (!_isGameOver.value) {
                    updateGame()
                }
                delay(16) // ~60 FPS
            }
        }
    }

    private fun loadTowerData() {
        if (towerName == null) return

        val sharedPref = getApplication<Application>().getSharedPreferences("GamePrefs", Context.MODE_PRIVATE)
        val json = sharedPref.getString("towers", null)
        if (json == null) return

        val type = object : TypeToken<List<Torre>>() {}.type
        val towers: List<Torre> = gson.fromJson(json, type)

        val currentTower = towers.find { it.name == towerName }
        if (currentTower != null) {
            _platformColor.value = currentTower.color
        }
    }

    private fun updateHighScore() {
        val sharedPref = getApplication<Application>().getSharedPreferences("GamePrefs", Context.MODE_PRIVATE) ?: return
        val json = sharedPref.getString("towers", null)
        val type = object : TypeToken<MutableList<Torre>>() {}.type
        val towers: MutableList<Torre> = if (json == null) mutableListOf() else gson.fromJson(json, type)

        val towerIndex = towers.indexOfFirst { it.name == towerName }
        if (towerIndex != -1) {
            val currentTower = towers[towerIndex]
            if (_score.value > currentTower.puntuacion) {
                towers[towerIndex] = currentTower.copy(puntuacion = _score.value)

                with(sharedPref.edit()) {
                    putString("towers", gson.toJson(towers))
                    apply()
                }
            }
        }
    }

    fun setScreenSize(width: Float, height: Float) {
        if (!screenSizeSet) {
            screenWidth = width
            screenHeight = height
            _ballPosition.value = Offset(width / 2, height / 4)
            screenSizeSet = true
        }
    }

    private fun updateGame() {
        if (!screenSizeSet) return

        var currentBallPosition = _ballPosition.value
        currentBallPosition += ballVelocity

        if (currentBallPosition.x < 0 || currentBallPosition.x > screenWidth) {
            ballVelocity = ballVelocity.copy(x = -ballVelocity.x)
        }
        if (currentBallPosition.y < 0) {
            ballVelocity = ballVelocity.copy(y = -ballVelocity.y)
        }

        val platformWidth = 300f
        val platformY = screenHeight - 50f
        val platformX = (screenWidth - platformWidth) / 2 + _platformOffset.value.x

        if (currentBallPosition.y >= platformY && currentBallPosition.y <= platformY + 50f &&
            currentBallPosition.x >= platformX && currentBallPosition.x <= platformX + platformWidth
        ) {
            ballVelocity = ballVelocity.copy(y = -ballVelocity.y)
            _score.value++
        }

        if (currentBallPosition.y > screenHeight) {
            _isGameOver.value = true
            updateHighScore()
        }

        _ballPosition.value = currentBallPosition
    }

    fun restartGame() {
        _ballPosition.value = Offset(screenWidth / 2, screenHeight / 4)
        ballVelocity = Offset(x = 5f, y = 5f)
        _score.value = 0
        _isGameOver.value = false
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
            val platformWidth = 300f
            val rotation = event.values[1]
            val currentOffset = _platformOffset.value
            val maxOffset = (screenWidth - platformWidth) / 2
            val newX = (currentOffset.x - rotation * 20).coerceIn(-maxOffset, maxOffset)
            _platformOffset.value = currentOffset.copy(x = newX)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this)
    }
}
