package com.example.musicplayer.vm

import androidx.lifecycle.ViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.musicplayer.model.Song
import com.example.musicplayer.worker.SongReminderWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class WorkViewModel @Inject constructor(
    val workManager: WorkManager
) : ViewModel() {
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