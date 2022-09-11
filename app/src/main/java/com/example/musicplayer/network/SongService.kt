package com.example.musicplayer.network

import retrofit2.Response
import retrofit2.http.GET

interface SongService {
    @GET("musicplayer/songs")
    suspend fun getSong(): Response<SongResponse>
}