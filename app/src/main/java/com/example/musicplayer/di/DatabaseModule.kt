package com.example.musicplayer.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.musicplayer.db.MusicDao
import com.example.musicplayer.db.MusicDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides

    fun provideTeaDatabase(app: Application): MusicDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            MusicDatabase::class.java,
            "music.db"
        ).build()
    }

    @Provides
    @Singleton

    fun provideSongDao(db: MusicDatabase): MusicDao {
        return db.songDao()
    }


    @Provides
    @Singleton
    fun provideSharePreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("music", Context.MODE_PRIVATE)
    }
}