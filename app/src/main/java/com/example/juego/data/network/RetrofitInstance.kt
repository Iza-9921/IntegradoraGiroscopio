package com.example.juego.data.network

import androidx.compose.ui.graphics.Color
import com.example.juego.ui.model.ColorAdapter
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.0.9:5000/" //tu ip

    private val gson = GsonBuilder()
        .registerTypeAdapter(Color::class.java, ColorAdapter())
        .create()

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}
