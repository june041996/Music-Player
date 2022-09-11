package com.example.musicplayer.vm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.db.MusicDatabase
import com.example.musicplayer.model.Favourite
import com.example.musicplayer.model.Song
import com.example.musicplayer.repository.FavouriteRepository
import com.example.musicplayer.utils.Contanst
import kotlinx.coroutines.launch

class FavouriteViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = MusicDatabase.getInstance(getApplication()).songDao()
    private val favouriteRepository = FavouriteRepository(getApplication())
    val SHARED_PREFS = "shared_prefs"
    private var sharedpreferences: SharedPreferences =
        getApplication<Application>().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    private val id = sharedpreferences.getInt("id", 0)
    private val name = sharedpreferences.getString("username", null)

    //insert
    fun insertFavourite(song: Song) {
        viewModelScope.launch {
            favouriteRepository.insertFavourite(Favourite(1, song.idSong!!))
        }
    }

    //get all songs
    val _songs = MutableLiveData<ArrayList<Song>>()
    val songs: LiveData<ArrayList<Song>>
        get() = getAllSongs()

    fun getAllSongs(): MutableLiveData<ArrayList<Song>> {
        Log.d(Contanst.TAG, "id: ${id.toString()} - name: $name")
        var list = arrayListOf<Song>()
        viewModelScope.launch {
            favouriteRepository.getSongsOfFavourite(1).forEach {
                list.addAll(it.songs)
            }
            _songs.value = list
            _sizeFvouriteSongs.value = list.size
            Log.d(Contanst.TAG, "it: ${_songs.value.toString()}")
        }
        return _songs
    }

    //delete
    fun removeFavouriteSong(song: Song) {
        viewModelScope.launch {
            favouriteRepository.deleteFavouriteSong(1, song.idSong!!)
            getAllSongs()
        }
    }

    //size
    val sizeFvouriteSongs: LiveData<Int>
        get() = _sizeFvouriteSongs
    var _sizeFvouriteSongs = MutableLiveData<Int>()

    var check: Boolean = false
    val _checkSong = MutableLiveData<Favourite>()
    val checkSong: LiveData<Favourite>
        get() = _checkSong

    fun checkFavouriteSong(idSong: Int) {

        check = false
        viewModelScope.launch {
            val a = favouriteRepository.getFavouriteSong(1, idSong)
            _checkSong.value = a
            if (a != null) {
                check = true
            }

        }
    }
}