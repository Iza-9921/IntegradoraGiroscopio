package com.example.juego.data.model

import com.google.gson.annotations.SerializedName

data class GameRecord(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("player_id") val playerId: Int,
    @SerializedName("score") val score: Int,
    @SerializedName("date") val date: String
)