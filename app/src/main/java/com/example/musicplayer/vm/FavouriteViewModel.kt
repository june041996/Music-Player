package com.example.musicplayer.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.db.MusicDatabase
import com.example.musicplayer.model.Favourite
import com.example.musicplayer.model.Song
import com.example.musicplayer.utils.Contanst
import kotlinx.coroutines.launch

class FavouriteViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = MusicDatabase.getInstance(getApplication()).songDao()

    //insert
    fun insertFavourite(song: Song) {
        viewModelScope.launch {
            dao.insertFavourite(Favourite(1, song.idSong!!))
        }
    }

    val _songs = MutableLiveData<ArrayList<Song>>()
    val songs: LiveData<ArrayList<Song>>
        get() = getAllSongs()

    fun getAllSongs(): MutableLiveData<ArrayList<Song>> {
        var list = arrayListOf<Song>()
        viewModelScope.launch {
            dao.getSongsOfFavourite(1).forEach {
                list.addAll(it.songs)
            }
            _songs.value = list
            Log.d(Contanst.TAG, "it: ${_songs.value.toString()}")
        }
        return _songs
    }
    //delete

    fun removeFavouriteSong(song: Song) {
        viewModelScope.launch {
            dao.deleteFavouriteSong(1, song.idSong!!)
        }
    }
}