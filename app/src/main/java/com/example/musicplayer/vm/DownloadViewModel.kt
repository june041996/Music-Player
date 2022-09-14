package com.example.musicplayer.vm

import android.app.Application
import android.content.Intent
import android.media.MediaScannerConnection
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.db.MusicDao
import com.example.musicplayer.db.MusicDatabase
import com.example.musicplayer.model.Song
import com.example.musicplayer.repository.SongRepository
import com.example.musicplayer.service.DownloadService
import com.example.musicplayer.utils.Contanst
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File


class DownloadViewModel(app: Application) :
    AndroidViewModel(app) {
    val dao: MusicDao = MusicDatabase.getInstance(getApplication()).songDao()
    val songRepository = SongRepository(dao)
    var songDownload: Song? = null
    val intent = Intent(getApplication(), DownloadService::class.java)
    fun startDownloadService(song: Song) {
        songDownload = song
        intent.putExtra("download", song)
        Log.d(Contanst.TAG, "v start")
        ContextCompat.startForegroundService(getApplication(), intent)
    }

    fun stopDownloadService() {
        getApplication<Application>().stopService(intent)
        //Log.d(Contanst.TAG,"update ${songDownload!!.idSong.toString()} ")
    }

    fun updateUrlSong(urlSong: String, song: Song) {
        //Log.d(Contanst.TAG,"update ${songDownload!!.idSong.toString()} - $urlSong")
        viewModelScope.launch {
            songRepository.updateUrlSong(urlSong, song.idSong!!)
            delay(500L)
            val file = File(urlSong)
            MediaScannerConnection.scanFile(
                getApplication(), arrayOf(file.toString()),
                null, null
            )
        }

    }
}