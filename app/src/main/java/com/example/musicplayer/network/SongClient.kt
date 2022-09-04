package com.example.musicplayer.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Singleton pattern
/*
Trong 1 thời điểm chỉ có 1 instance SongClient
 */
object SongClient {
    private const val BASE_URL = "https://apimocha.com/rinphucquang/"

    /**
     * Retrofit lấy json về
     * Gson chuyển đổi json thành object
     */
    operator fun invoke(): SongService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SongService::class.java)
}