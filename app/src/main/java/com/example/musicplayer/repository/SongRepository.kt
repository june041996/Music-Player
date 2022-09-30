package com.example.musicplayer.repository

import com.example.musicplayer.db.MusicDao
import com.example.musicplayer.model.Song
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongRepository @Inject constructor(val dao: MusicDao) {


    suspend fun insertSong(song: Song) = dao.insertSong(song)


    suspend fun deleteSong(song: Song) = dao.deleteSong(song.idSong!!)


    suspend fun getLocalSongs(): List<Song> = dao.getLocalSongs(true)


    suspend fun getAllSongs(): List<Song> = dao.getSongs()


    suspend fun updateUrlSong(urlSong: String, idSong: Int) =
        dao.updateUrlSong(urlSong, true, idSong)


    suspend fun getSongByName(name: String): Song = dao.getSongByName(name)

}