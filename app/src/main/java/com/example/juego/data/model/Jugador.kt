package com.example.juego.data.model

import com.google.gson.annotations.SerializedName

data class Jugador(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("current_theme")
    val currentTheme: String = "wood"
)