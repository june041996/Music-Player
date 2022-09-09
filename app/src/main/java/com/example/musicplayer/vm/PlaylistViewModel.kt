package com.example.musicplayer.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.db.MusicDatabase
import com.example.musicplayer.model.Playlist
import kotlinx.coroutines.launch

class PlaylistViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = MusicDatabase.getInstance(getApplication()).songDao()

    //get all
    val playlists: LiveData<ArrayList<Playlist>>
        get() = getAllPlaylist()

    private fun getAllPlaylist(): MutableLiveData<ArrayList<Playlist>> {
        val _playlist = MutableLiveData<ArrayList<Playlist>>()
        viewModelScope.launch {
            dao.getUserWithPlaylistsAndSongs(1).playlists.forEach {
                _playlist.value?.add(it.playlist)
            }

        }
        return _playlist
    }

    //insert
}