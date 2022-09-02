package com.example.musicplayer.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.musicplayer.model.Favourite
import com.example.musicplayer.model.Playlist
import com.example.musicplayer.model.Song
import com.example.musicplayer.model.User
import com.example.musicplayer.model.relation.FavouriteSongCrossRef
import com.example.musicplayer.model.relation.SongPlaylistCrossRef


@Database(
    entities = [User::class, Song::class,
        Playlist::class, SongPlaylistCrossRef::class,
        Favourite::class, FavouriteSongCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class MusicDatabase : RoomDatabase() {

    abstract fun songDao(): MusicDao

    companion object {
        private var instance: MusicDatabase? = null

        //singleton pattern
        @Synchronized
        fun getInstance(ctx: Context): MusicDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    MusicDatabase::class.java,
                    "music.db"
                )
                    .build()
            }
            return instance!!
        }
    }
}