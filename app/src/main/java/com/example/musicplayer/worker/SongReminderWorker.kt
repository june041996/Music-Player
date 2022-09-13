package com.example.musicplayer.worker


import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.musicplayer.utils.Contanst
import com.example.musicplayer.utils.NotificationHelper
import java.util.*


class SongReminderWorker(val context: Context, val params: WorkerParameters) :
    Worker(context, params) {

    override fun doWork(): Result {
        val size = inputData.getStringArray("message")!!.size
        val i = Random().nextInt(size - 0) + 0
        Log.d(Contanst.TAG, "random: ${i.toString()}")

        //set notification with title and message
        NotificationHelper(context).createNotification(

            inputData.getString("title").toString(),
            inputData.getStringArray("message")?.get(i).toString()
        )
        return Result.success()
    }
}