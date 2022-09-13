package com.example.musicplayer.repository

import android.content.Context
import com.example.musicplayer.db.MusicDao
import com.example.musicplayer.db.MusicDatabase
import com.example.musicplayer.model.Song

class SongRepository(val context: Context) {
    var dao: MusicDao = MusicDatabase.getInstance(context).songDao()

    suspend fun insertSong(song: Song) {
        dao.insertSong(song)
    }

    suspend fun deleteSong(song: Song) {
        dao.deleteSong(song.idSong!!)
    }

    suspend fun getLocalSongs(): List<Song> {
        return dao.getLocalSongs(true)
    }

    suspend fun getAllSongs(): List<Song> {
        return dao.getSongs()
    }
}