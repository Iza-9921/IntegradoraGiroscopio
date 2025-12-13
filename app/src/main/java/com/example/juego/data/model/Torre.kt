package com.example.juego.ui.model

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName

data class Torre(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("player_id") val playerId: Int? = null,
    @SerializedName("name") val name: String,
    @SerializedName("color") val color: Color,
    @SerializedName("score") val puntuacion: Int = 0
)
