package com.example.juego.data.network

import com.example.juego.data.model.Jugador
import com.example.juego.ui.model.Torre
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

interface ApiService {

    // Crear un jugador nuevo con datos Multipart (texto + posible imagen)
    @Multipart
    @POST("players")
    suspend fun createPlayer(
        // Mapa con los campos del jugador (nombre, email, etc.)
        @PartMap parts: Map<String, @JvmSuppressWildcards RequestBody>,
        // Avatar opcional enviado como archivo
        @Part avatar: MultipartBody.Part? = null
    ): Response<Jugador>

    // Login de un jugador usando un map simple con email y password
    @POST("players/login")
    suspend fun login(@Body loginRequest: Map<String, String>): Response<Jugador>

    // Obtener todas las torres pertenecientes a un jugador
    @GET("towers/player/{playerId}")
    suspend fun getTowersByPlayer(
        // ID del jugador que queremos consultar
        @Path("playerId") playerId: Int
    ): Response<List<Torre>>

    // Crear una torre nueva enviando el objeto Torre completo
    @POST("towers")
    suspend fun createTower(
        @Body tower: Torre
    ): Response<Torre>

    // Actualizar una torre existente por ID
    @PUT("towers/{id}")
    suspend fun updateTower(
        // ID de la torre a modificar
        @Path("id") id: Int,
        // Nuevo contenido de la torre
        @Body tower: Torre
    ): Response<Torre>

    // Eliminar una torre por ID
    @DELETE("towers/{id}")
    suspend fun deleteTower(
        @Path("id") id: Int
    ): Response<Void>
}
