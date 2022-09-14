package com.example.musicplayer.vm

import android.app.Application
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.*
import com.example.musicplayer.db.MusicDao
import com.example.musicplayer.model.Song
import com.example.musicplayer.network.SongClient
import com.example.musicplayer.repository.SongRepository
import com.example.musicplayer.utils.Contanst
import com.example.musicplayer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    val dao: MusicDao,
    val prefs: SharedPreferences,
    application: Application
) : AndroidViewModel(application) {

    val songRepository = SongRepository(dao)
    private val id = prefs.getInt("id", 0)
    private val name = prefs.getString("username", null)

    companion object {
        private const val TAG: String = "DHP"
    }

    //Rin
    ////////////////////////////////////////////////////////////////////////////////

    //GET Song from API
    private suspend fun getSongFromAPI() = SongClient.invoke().getSong()

    fun getSong() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val data = getSongFromAPI().body()?.songList
            emit(Resource.success(data = data))
        } catch (ex: Exception) {
            emit(Resource.error(data = null, message = ex.message ?: "Error !!"))
        }
    }

    //INSERT Song to Database

    private suspend fun insertSongDao(song: Song) = songRepository.insertSong(song)


    fun insertSongToDB(song: Song) = viewModelScope.launch {
        insertSongDao(song)
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    //Anh Phuc

    private fun insertSong(song: Song) {
        viewModelScope.launch {
            songRepository.insertSong(song)
        }
    }

    private fun deleteSong(song: Song) {
        viewModelScope.launch {
            songRepository.deleteSong(song)
        }
    }

    //get all songs
    //get all songs
    val _songs = MutableLiveData<ArrayList<Song>>()
    val songs: LiveData<ArrayList<Song>>
        get() = getAllSongs()

    fun getAllSongs(): MutableLiveData<ArrayList<Song>> {
        Log.d(Contanst.TAG, "id: ${id.toString()} - name: $name")
        var list = arrayListOf<Song>()
        viewModelScope.launch {
            songRepository.getAllSongs().forEach {
                list.add(it)
            }
            _songs.value = list
            Log.d(Contanst.TAG, "it: ${_songs.value.toString()}")
        }
        return _songs
    }

    //get song by name
    val _songByName = MutableLiveData<Song>()
    val songByName: LiveData<Song>
        get() = _songByName

    fun getSongByName(name: String) {
        viewModelScope.launch {
            val song = songRepository.getSongByName(name)
            _songByName.value = song
        }
    }

    //get all local song from room database
    val localSongs: LiveData<List<Song>>
        get() = getLocalSongs()
    var _localSong = MutableLiveData<List<Song>>()
    private fun getLocalSongs(): MutableLiveData<List<Song>> {
        viewModelScope.launch {
            _localSong.value = songRepository.getLocalSongs()
            _sizeLocalSongs.value = _localSong.value!!.size
        }
        return _localSong
    }

    //
    val sizeLocalSongs: LiveData<Int>
        get() = _sizeLocalSongs
    val _sizeLocalSongs = MutableLiveData<Int>()

    //update local song
    fun updateLocalSongs() {

        val oldSongs = _localSong.value
        val newSongs: ArrayList<Song> = arrayListOf()
        //get local song from device
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf<String>(
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media._ID,
        )
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
        val c: Cursor? = getApplication<Application>().contentResolver.query(
            uri, projection, selection, null, null
        )
        if (c != null) {
            Log.d(TAG, "not null")
            while (c.moveToNext()) {
                //val audioModel = AudioModel()
                val path = c.getString(0)
                val name = c.getString(1)
                val artist = c.getString(2)
                val display_name = c.getString(3)
                val duration = c.getString(4)
                val album = c.getString(5)
                val id = c.getString(6)
                if (path != null && path.endsWith(".mp3")) {
                    Log.d(
                        TAG,
                        "id :${id}, name :${name}, data :${path}, display_name :${display_name}, duration :${duration}"
                    )
                    val song = Song(
                        id.toInt(),
                        name,
                        "",
                        path,
                        "",
                        "",
                        artist,
                        "",
                        null,
                        duration,
                        0,
                        true
                    )
                    newSongs.add(song)
                }
            }
            c.close()
        } else {
            Log.d(TAG, "null")
        }
        Log.d(TAG, "old: ${oldSongs.toString()}")
        Log.d(TAG, "new: ${newSongs.toString()}")
        //update local song between device and room
        val tempOld = arrayListOf<Song>()
        val tempNew = arrayListOf<Song>()

        if (oldSongs != null) {
            tempOld.addAll(oldSongs)
        }
        tempNew.addAll(newSongs)

        tempOld.removeAll(newSongs)//delete
        if (oldSongs != null) {
            tempNew.removeAll(oldSongs)//add
        }
        Log.d(TAG, "delete: ${tempOld.toString()}")
        Log.d(TAG, "add: ${tempNew.toString()}")
        tempOld.forEach { deleteSong(it) }
        tempNew.forEach { insertSong(it) }
    }

    var _selectedSong = MutableLiveData<Song>()
    val selectedSong: LiveData<Song>
        get() = _selectedSong

    fun setSelectSong(song: Song) {
        _selectedSong.value = song
    }


//    ////////////////
//    //get all songs
//    val _songs = MutableLiveData<ArrayList<Song>>()
//    val songs: LiveData<ArrayList<Song>>
//        get() = getAllSongs()
//
//    fun getAllSongs(): MutableLiveData<ArrayList<Song>> {
//        Log.d(Contanst.TAG, "id: ${id.toString()} - name: $name")
//        var list = arrayListOf<Song>()
//        viewModelScope.launch {
//            songRepository.getAllSongs().forEach {
//                list.add(it)
//            }
//            _songs.value = list
//            Log.d(Contanst.TAG, "it: ${_songs.value.toString()}")
//        }
//        return _songs
//    }
}