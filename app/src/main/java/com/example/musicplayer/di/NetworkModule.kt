package com.example.musicplayer.di


import com.example.musicplayer.network.SongService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            //https://apimocha.com/phucdh11/user
            .baseUrl("https://apimocha.com/phucdh11/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSongService(retrofit: Retrofit): SongService {
        return retrofit.create(SongService::class.java)
    }
}