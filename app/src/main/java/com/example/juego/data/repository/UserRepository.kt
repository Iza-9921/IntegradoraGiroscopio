package com.example.juego.data.repository

import com.example.juego.data.model.Jugador
import com.example.juego.data.network.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class UserRepository {

    // cliente retrofit para llamadas a la api
    private val api = RetrofitClient.instance

    // realiza el login del usuario con username y password
    suspend fun login(username: String, password: String): Result<Jugador> {
        return try {
            val response = api.login(
                mapOf(
                    "username" to username,
                    "password" to password
                )
            )

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("login fallido: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // registra un nuevo jugador en el sistema
    suspend fun register(player: Jugador): Result<Jugador> {
        return try {
            // convierte los datos del jugador a requestbody
            val username = player.username.toRequestBody("text/plain".toMediaTypeOrNull())
            val password = player.password.toRequestBody("text/plain".toMediaTypeOrNull())
            val email = player.email.toRequestBody("text/plain".toMediaTypeOrNull())
            val currentTheme = player.currentTheme.toRequestBody("text/plain".toMediaTypeOrNull())

            // mapa con los campos a enviar al servidor
            val parts = mapOf(
                "username" to username,
                "password" to password,
                "email" to email,
                "current_theme" to currentTheme
            )

            // no se envia avatar(una imagen como para el perfil)
            val response = api.createPlayer(parts, null)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("registro fallido: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
