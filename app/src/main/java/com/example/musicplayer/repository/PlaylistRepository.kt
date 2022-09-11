package com.example.musicplayer.repository

import android.content.Context
import com.example.musicplayer.db.MusicDatabase
import com.example.musicplayer.model.Playlist
import com.example.musicplayer.model.relation.PlaylistWithSongs
import com.example.musicplayer.model.relation.SongPlaylistCrossRef
import com.example.musicplayer.model.relation.UserWithPlaylistsAndSongs

class PlaylistRepository(val context: Context) {
    val dao = MusicDatabase.getInstance(context).songDao()

    suspend fun insertPlaylist(playlist: Playlist) {
        dao.insertPlaylist(playlist)
    }

    suspend fun updatePlaylist(namePlaylist: String, idPlaylist: Int) {
        dao.updatePlaylist(namePlaylist, idPlaylist)
    }

    suspend fun deletePlaylist(idPlaylist: Int) {
        dao.deletePlaylist(idPlaylist)
        dao.deletePlaylistSongs(idPlaylist)
    }

    suspend fun deleteSongOfPlaylist(idPlaylist: Int, idSong: Int) {
        dao.deleteSongOfPlaylist(idPlaylist, idSong)
    }

    suspend fun getSongsOfPlaylist(idPlaylist: Int): List<PlaylistWithSongs> {
        return dao.getSongsOfPlaylist(idPlaylist)
    }

    suspend fun getUserWithPLaylistAndSongs(idUser: Int): UserWithPlaylistsAndSongs {
        return dao.getUserWithPlaylistsAndSongs(idUser)
    }

    suspend fun insertSongPlaylistCrossRef(idSong: Int, idPlaylist: Int) {
        dao.insertSongPlaylistCrossRef(SongPlaylistCrossRef(idSong, idPlaylist))
    }
}