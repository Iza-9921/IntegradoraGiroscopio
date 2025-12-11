package com.example.juego.data.repository

import com.example.juego.data.model.Jugador
import com.example.juego.data.network.RetrofitClient

class UserRepository {
    private val api = RetrofitClient.instance

    suspend fun login(username: String, password: String): Result<Jugador> {
        return try {
            val response = api.login(mapOf("username" to username, "password" to password))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Login fallido: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(player: Jugador): Result<Jugador> {
        return try {
            // Se envían los campos por separado para coincidir con @FormUrlEncoded de la API
            val response = api.createPlayer(
                username = player.username,
                password = player.password,
                email = player.email,
                currentTheme = player.currentTheme
            )
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Registro fallido: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updatePlayer(id: Int, player: Jugador): Result<Jugador> {
        return try {
            val response = api.updatePlayer(id, player)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Actualización fallida: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deletePlayer(id: Int): Result<Boolean> {
        return try {
            val response = api.deletePlayer(id)
            if (response.isSuccessful) {
                Result.success(true)
            } else {
                Result.failure(Exception("Eliminación fallida: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
