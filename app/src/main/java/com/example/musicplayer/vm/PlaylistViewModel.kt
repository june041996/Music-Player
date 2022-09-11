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
import com.example.musicplayer.repository.PlaylistRepository
import com.example.musicplayer.repository.SongRepository
import com.example.musicplayer.utils.Contanst
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlaylistViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = MusicDatabase.getInstance(getApplication()).songDao()
    val playlistRepository = PlaylistRepository(getApplication<Application>().applicationContext)
    val songRepository = SongRepository(getApplication<Application>().applicationContext)

    //size songs of playlist
    val sizeSongs: LiveData<Int>
        get() = _sizeSongs
    var _sizeSongs = MutableLiveData<Int>()

    //get song of playlist
    var _songsOfPlaylist = MutableLiveData<ArrayList<Song>>()
    val songsOfPlaylist: LiveData<ArrayList<Song>>
        get() = getSongsOfPlaylist()
    val listSongs = arrayListOf<Song>()
    fun getSongsOfPlaylist(): MutableLiveData<ArrayList<Song>> {
        listSongs.clear()
        viewModelScope.launch {

            playlistRepository.getSongsOfPlaylist(_selectedPlaylist.value!!.idPlaylist!!).forEach {
                listSongs.addAll(it.songs)
            }
            _songsOfPlaylist.value = listSongs
            _sizeSongs.value = listSongs.size
        }
        return _songsOfPlaylist
    }

    //get suggest songs = all songs - songs of playlist
    var _songs = MutableLiveData<ArrayList<Song>>()
    val songs: LiveData<ArrayList<Song>>
        get() = getAllSongs()
    val list = arrayListOf<Song>()
    fun getAllSongs(): MutableLiveData<ArrayList<Song>> {
        viewModelScope.launch {
            list.clear()
            list.addAll(songRepository.getAllSongs())
            _songs.value = list
        }
        return _songs
    }

    val _suggestSongs = MutableLiveData<ArrayList<Song>>()
    val suggestSongs: LiveData<ArrayList<Song>>
        get() = getSuggestSongs()

    fun getSuggestSongs(): MutableLiveData<ArrayList<Song>> {
        viewModelScope.launch {
            getAllSongs()
            //getSongsOfPlaylist()
            delay(500L)
            val temp = arrayListOf<Song>()
            temp.addAll(list)

            var songsOfPlaylist = arrayListOf<Song>()
            songsOfPlaylist = listSongs

            // Log.d(Contanst.TAG,temp.size.toString())
            // Log.d(Contanst.TAG,songsOfPlaylist.size.toString())
            temp.removeAll(songsOfPlaylist)
            _suggestSongs.value = temp
            // Log.d(Contanst.TAG,temp.size.toString())
        }
        return _suggestSongs
    }

    //update playlist
    fun updatePlaylist(name: String, id: Int) {
        viewModelScope.launch {
            playlistRepository.updatePlaylist(name, id)
            getAllPlaylist()
        }
    }

    //delete playlist
    fun deletePlaylist(id: Int) {
        viewModelScope.launch {
            playlistRepository.deletePlaylist(id)
            getAllPlaylist()
        }
    }

    //delete song of playlist
    fun deleteSongOfPlaylist(song: Song) {
        viewModelScope.launch {
            Log.d(Contanst.TAG, "${_selectedPlaylist.value.toString()}- ${song.idSong.toString()}")
            playlistRepository.deleteSongOfPlaylist(
                _selectedPlaylist.value!!.idPlaylist!!,
                song.idSong!!
            )
            getSongsOfPlaylist()
            getSuggestSongs()
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
            playlistRepository.getUserWithPLaylistAndSongs(1).playlists.forEach {
                temp.add(it.playlist)
            }
            _playlist.value = temp
        }
        return _playlist
    }

    //insert
    fun insertPlaylist(name: String) {
        val playlist = Playlist(null, 1, name, "1")
        viewModelScope.launch {
            playlistRepository.insertPlaylist(playlist)
            getAllPlaylist()
        }
    }

    fun insertSongPlaylist(idSong: Int, idPlaylist: Int) {
        viewModelScope.launch {
            playlistRepository.insertSongPlaylistCrossRef(idSong, idPlaylist)
            getSuggestSongs()
            getSongsOfPlaylist()
        }
    }
}