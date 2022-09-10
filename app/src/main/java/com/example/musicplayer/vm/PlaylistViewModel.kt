package com.example.musicplayer.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.db.MusicDatabase
import com.example.musicplayer.model.Playlist
import com.example.musicplayer.model.Song
import com.example.musicplayer.model.relation.SongPlaylistCrossRef
import com.example.musicplayer.utils.Contanst
import kotlinx.coroutines.launch

class PlaylistViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = MusicDatabase.getInstance(getApplication()).songDao()

    //get song of playlist
    var _songsOfPlaylist = MutableLiveData<ArrayList<Song>>()
    val songsOfPlaylist: LiveData<ArrayList<Song>>
        get() = getSongsOfPlaylist()

    fun getSongsOfPlaylist(): MutableLiveData<ArrayList<Song>> {
        val temp = arrayListOf<Song>()
        viewModelScope.launch {
            dao.getSongsOfPlaylist(_selectedPlaylist.value!!.idPlaylist!!).forEach {
                temp.addAll(it.songs)
            }
            _songsOfPlaylist.value = temp
        }
        return _songsOfPlaylist
    }

    //get suggest songs = all songs - songs of playlist
    var _songs = MutableLiveData<ArrayList<Song>>()
    val songs: LiveData<ArrayList<Song>>
        get() = _songs

    fun getAllSongs() {
        viewModelScope.launch {
            val list = arrayListOf<Song>()
            list.addAll(dao.getSongs())
            _songs.value = list
        }
    }

    val suggestSongs: LiveData<ArrayList<Song>>
        get() = getSuggestSongs()

    private fun getSuggestSongs(): MutableLiveData<ArrayList<Song>> {
        val _suggestSongs = MutableLiveData<ArrayList<Song>>()

        return _suggestSongs
    }

    //delete song of playlist
    fun deleteSongOfPlaylist(song: Song) {
        viewModelScope.launch {
            Log.d(Contanst.TAG, "${_selectedPlaylist.value.toString()}- ${song.idSong.toString()}")
            dao.deleteSongOfPlaylist(_selectedPlaylist.value!!.idPlaylist!!, song.idSong!!)
            getSongsOfPlaylist()
        }
    }

    var _selectedPlaylist = MutableLiveData<Playlist>()
    val selectedPlaylist: LiveData<Playlist>
        get() = _selectedPlaylist

    fun setSelectedPlaylist(playlist: Playlist) {
        _selectedPlaylist.value = playlist
    }

    //get all
    val _playlist = MutableLiveData<ArrayList<Playlist>>()
    val playlists: LiveData<ArrayList<Playlist>>
        get() = getAllPlaylist()

    private fun getAllPlaylist(): MutableLiveData<ArrayList<Playlist>> {

        val temp = arrayListOf<Playlist>()
        viewModelScope.launch {
            val l = dao.getUserWithPlaylistsAndSongs(1).playlists
            _playlist.value = temp
        }
        return _playlist
    }

    //insert
    fun insertPlaylist(name: String) {
        val playlist = Playlist(null, 1, name, "1")
        viewModelScope.launch {
            dao.insertPlaylist(playlist)
            getAllPlaylist()
        }
    }

    fun insertSongPlaylist(idSong: Int, idPlaylist: Int) {
        viewModelScope.launch {
            dao.insertSongPlaylistCrossRef(SongPlaylistCrossRef(idSong, idPlaylist))
        }
    }
}