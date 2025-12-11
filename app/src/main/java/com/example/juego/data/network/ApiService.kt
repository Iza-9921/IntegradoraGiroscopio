package com.example.juego.data.network


import com.example.juego.data.model.GameRecord
import com.example.juego.data.model.Jugador
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("players")
    suspend fun createPlayer(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("current_theme") currentTheme: String
    ): Response<Jugador>

    @PUT("players/{id}")
    suspend fun updatePlayer(@Path("id") id: Int, @Body player: Jugador): Response<Jugador>

    @DELETE("players/{id}")
    suspend fun deletePlayer(@Path("id") id: Int): Response<Void>

    @POST("players/login")
    suspend fun login(@Body loginRequest: Map<String, String>): Response<Jugador>

    @GET("records/player/{playerId}")
    suspend fun getRecordsByPlayer(@Path("playerId") playerId: Int): Response<List<GameRecord>>

    @FormUrlEncoded
    @POST("records")
    suspend fun createRecord(
        @Field("player_id") playerId: Int,
        @Field("score") score: Int,
        @Field("date") date: String
    ): Response<GameRecord>
}
