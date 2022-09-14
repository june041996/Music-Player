package com.example.musicplayer.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.musicplayer.db.MusicDao
import com.example.musicplayer.db.MusicDatabase
import com.example.musicplayer.model.Song
import com.example.musicplayer.repository.SongRepository
import com.example.musicplayer.worker.SongReminderWorker
import java.util.concurrent.TimeUnit


class WorkViewModel(app: Application) :
    AndroidViewModel(app) {
    val dao: MusicDao = MusicDatabase.getInstance(getApplication()).songDao()
    private val workManager = WorkManager.getInstance(app.applicationContext)
    private val songRepository = SongRepository(dao)
    val names: ArrayList<String> = arrayListOf()
    fun enqueuePeriodicReminder(songs: List<Song>) {
        songs.forEach {
            names.add(it.nameSong!!)
        }
        //create request with input data
        val request = PeriodicWorkRequestBuilder<SongReminderWorker>(15, TimeUnit.MINUTES)
            .setInputData(
                workDataOf(
                    "title" to "Open app to play this song",
                    "message" to names.toTypedArray()
                )
            )
        workManager.enqueueUniquePeriodicWork(
            "SAMPLE",
            ExistingPeriodicWorkPolicy.REPLACE,
            request.build()
        )
    }

    fun cancel() {
        workManager.cancelAllWork()
    }

}