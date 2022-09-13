package com.example.musicplayer.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicplayer.db.MusicDao
import com.example.musicplayer.model.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicPlayerViewModel @Inject constructor(val songDao: MusicDao, application: Application) :
    AndroidViewModel(application) {
    companion object {
        private const val LOG: String = "TCR"
    }


    val songs = songDao.getAllSongs()

    //Lấy id từ Fragment
    private val _idSong = MutableLiveData<Int>()

    fun setIdSong(id: Int) {
        _idSong.value = id
    }

    val idSong: LiveData<Int> = _idSong

    //Nhận id ở MusicPlayer

    fun songById(id: Int): LiveData<Song> {
        return songDao.getSongById(id)
    }


//    fun getSongById(id: Int) = liveData(Dispatchers.Main) {
//        emit(Resource.loading(data = null))
//        try {
//            val data = songDao.getSongById(id)
//            emit(Resource.success(data = data))
//        } catch (ex: Exception) {
//            emit(Resource.error(data = null, message = ex.message ?: "Error !!"))
//        }
//    }

}