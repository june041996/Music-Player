package com.example.musicplayer.vm

import android.app.Application
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.db.MusicDatabase
import com.example.musicplayer.model.Song
import kotlinx.coroutines.launch

class HomeViewModel(app: Application) : AndroidViewModel(app) {
    private val TAG: String = "DHP"
    val dao = MusicDatabase.getInstance(getApplication()).songDao()

    private fun insertSong(song: Song) {
        viewModelScope.launch {
            dao.insertSong(song)
        }
    }

    private fun deleteSong(song: Song) {
        viewModelScope.launch {
            dao.deleteSong(song.idSong!!)
        }
    }

    var _localSong = MutableLiveData<List<Song>>()

    //get all local song from room database
    val localSongs: LiveData<List<Song>>
        get() = getLocalSongs()

    private fun getLocalSongs(): MutableLiveData<List<Song>> {
        viewModelScope.launch {
            _localSong.value = dao.getLocalSongs(true)
        }
        return _localSong
    }

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
}