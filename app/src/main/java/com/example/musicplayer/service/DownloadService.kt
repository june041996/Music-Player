package com.example.musicplayer.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.musicplayer.R
import com.example.musicplayer.activity.MusicPlayerActivity
import com.example.musicplayer.model.Song
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class DownloadService : Service() {
    private val TAG: String = "DHP"
    val CHANNEL_ID = "DownloadServiceChannel"
    private lateinit var builder: NotificationCompat.Builder
    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "onBind")
        return null
    }

    var song: Song? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //HttpsTrustManager.allowAllSSL()
        Log.d(TAG, "onStartCommand")
        song = intent!!.getSerializableExtra("download") as Song
        Log.d("DHP", "song s: ${song.toString()}")
        createNotificationChannel()
        createNotification(song!!.nameSong!!)
        Thread(object : Runnable {
            override fun run() {
                // download("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
                //download("https://firebasestorage.googleapis.com/v0/b/music-player-97edf.appspot.com/o/BienNho-LeQuyen-5410854.mp3?alt=media&token=f4fc74a5-d247-4a32-a54d-6df2c97014b4")
                download(song!!)
            }
        }).start()
        return START_NOT_STICKY
    }

    // tạo notification
    private fun createNotification(input: String) {
        val notificationIntent = Intent(this, MusicPlayerActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(input)
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setProgress(100, 0, false)
        startForeground(1, builder.build())
    }

    private fun download(song: Song) {
        var input: InputStream? = null
        var output: OutputStream? = null
        var connection: HttpURLConnection? = null
        var file: File? = null
        try {
            val url = URL(song.urlSong)
            //val url = URL("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            var fileLength: Int
            var fileName = ""
            var extension = ""
            connection.apply {
                connect()
                /*if (responseCode != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "mess: ${responseMessage.toString()}")
                    return
                } else {
                    Log.e(TAG, "ok: ${responseMessage.toString()}")
                }*/
                fileLength = contentLength
                input = inputStream
                Log.d(TAG, "de:${fileLength.toString()} - ${input.toString()}")
                /*al disposition = getHeaderField("Content-Disposition")


                if (disposition != null) {
                    // extracts file name from header field
                    val index = disposition.indexOf("filename=")
                    if (index > 0) {
                        fileName = disposition.substring(
                            index + 10,
                            disposition.length - 1
                        )
                    }
                } else {
                    // extracts file name from URL

                }*/
                /*val contentType = getContentType()

                Log.e(TAG, "FILENAME : $fileName")
                Log.e(TAG, "CONTENT TYPE : $contentType")
                extension = contentType.split("/")[1]
                Log.e(TAG, "extension : ${extension.toString()}")*/
                fileName = Date().time.toString()

            }
            val sdCard = Environment.getExternalStorageDirectory()
                .toString() + "/" + Environment.DIRECTORY_DOWNLOADS
            file = File(sdCard, "$fileName.mp3")
            val link = "$sdCard/$fileName.mp3"

            Log.d(TAG, "link song: $sdCard/$fileName.mp3")
            output = FileOutputStream(file)

            val BUFFER_SIZE = 4096
            var bytesRead: Int
            val buffer = ByteArray(BUFFER_SIZE)
            var total: Long = 0
            while (input?.read(buffer).also {
                    bytesRead = it!!
                    total += it.toLong()
                    if (fileLength > 0 && it.toInt() != -1) {
                        // only if total length is known
                        Log.d(TAG, "start")
                        val percent = (total.toInt() / fileLength.toFloat()) * 100
                        if (percent.toInt() % 10 == 0) {
                            Log.d(TAG, "percent: ${percent.toInt().toString()}")
                            updateNotification(total.toInt(), fileLength, false, "Downloading")
                        }
                    } else {
                        Log.d(TAG, "completed")
                        //updateNotification(total.toInt(), fileLength, true, "Downloading")
                    }
                } != -1) {
                //Log.d(TAG,"write")
                output.write(buffer, 0, bytesRead)
            }
            output.flush()
            output.close()
            input?.close()
            Log.d(TAG, "completeeeed")

            sendMessageToActivity(link)
            updateNotification(0, 0, false, "Completed")
        } catch (e: Exception) {
            Log.e(TAG, "catch: ${e.toString()}")
            updateNotification(0, 0, false, "Invalid download link.")
        } finally {
            try {
                output?.apply { close() }
                input?.apply { close() }
            } catch (ignored: IOException) {
                Log.e(TAG, "ignored: ${ignored.toString()}")
                updateNotification(0, 0, false, "Invalid download link.")
            }
            connection?.apply { disconnect() }
        }
    }


    private fun updateNotification(
        currentProgress: Int,
        maxProgress: Int,
        indeterminate: Boolean,
        msg: String
    ) {

        builder.apply {
            Log.d(TAG, "$maxProgress-$currentProgress-$indeterminate-$msg")
            if (maxProgress != -1 && currentProgress == maxProgress) {
                Log.d(TAG, "0")
                setContentText("completed")
                // setOngoing(false)
                // setProgress(0, 0, indeterminate)
            } else {
                Log.d(TAG, "1")
                val percent = ((currentProgress.toFloat() / maxProgress.toFloat()) * 100).toInt()
                    .toString() + " %"
                setContentText(percent)
                //setOngoing(true)
            }
            setProgress(maxProgress, currentProgress, indeterminate)
        }
        startForeground(1, builder.build())
    }

    //tạo notification channel
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Download Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            serviceChannel.setSound(null, null)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun sendMessageToActivity(url: String) {
        val intent = Intent("Download")
        intent.putExtra("downloaded", url)
        intent.putExtra("song_downloaded", song)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
}