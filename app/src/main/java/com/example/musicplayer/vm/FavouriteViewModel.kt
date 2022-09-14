package com.example.musicplayer.vm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.db.MusicDao
import com.example.musicplayer.db.MusicDatabase
import com.example.musicplayer.model.Favourite
import com.example.musicplayer.model.Song
import com.example.musicplayer.repository.FavouriteRepository
import com.example.musicplayer.utils.Contanst
import kotlinx.coroutines.launch


class FavouriteViewModel(
    app: Application
) : AndroidViewModel(app) {

    val dao: MusicDao = MusicDatabase.getInstance(getApplication()).songDao()
    val prefs: SharedPreferences =
        getApplication<Application>().getSharedPreferences("music", Context.MODE_PRIVATE)
    private val favouriteRepository = FavouriteRepository(dao)

    private val id = prefs.getInt("id", 0)
    private val name = prefs.getString("username", null)


    //insert
    fun insertFavourite(song: Song) {
        viewModelScope.launch {
            favouriteRepository.insertFavourite(Favourite(id, song.idSong!!))
            checkFavouriteSong(song.idSong)
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
            favouriteRepository.getSongsOfFavourite(id).forEach {
                list.addAll(it.songs)
            }
            _songs.value = list
            _sizeFavouriteSongs.value = list.size
            Log.d(Contanst.TAG, "it: ${_songs.value.toString()}")
        }
        return _songs
    }

    //delete
    fun removeFavouriteSong(song: Song) {
        viewModelScope.launch {
            favouriteRepository.deleteFavouriteSong(id, song.idSong!!)
            getAllSongs()
        }
    }

    //size
    val sizeFavouriteSongs: LiveData<Int>
        get() = _sizeFavouriteSongs
    var _sizeFavouriteSongs = MutableLiveData<Int>()

    var check: Boolean = false
    val _checkSong = MutableLiveData<Favourite>()
    val checkSong: LiveData<Favourite>
        get() = _checkSong
    val checkFavourite: LiveData<Boolean>
        get() = _checkFavourite
    val _checkFavourite = MutableLiveData<Boolean>(false)
    fun checkFavouriteSong(idSong: Int) {
        check = false
        viewModelScope.launch {
            val a = favouriteRepository.getFavouriteSong(id, idSong)
            _checkSong.value = a
            if (a != null) {
                check = true
                _checkFavourite.value = true
            } else {
                _checkFavourite.value = false
            }

        }
    }

    fun checkFavouriteCheckUnCheck(song: Song) {
        val b = _checkFavourite.value
        if (b == true) {
            Log.d(Contanst.TAG, "remove: ${b.toString()}")
            removeFavouriteSong(song)
        } else {
            Log.d(Contanst.TAG, "insert: ${b.toString()}")
            insertFavourite(song)
        }
    }
}