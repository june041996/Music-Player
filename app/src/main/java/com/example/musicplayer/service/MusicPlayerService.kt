package com.example.musicplayer.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.musicplayer.R
import com.example.musicplayer.fragment.MusicPlayerFragment
import com.example.musicplayer.utils.formatSongDuration

class MusicPlayerService : Service() {
    private var myBinder = MyBinder()
    var mediaPlayer: MediaPlayer? = null
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var runnable: Runnable
    override fun onBind(p0: Intent?): IBinder? {
        mediaSession = MediaSessionCompat(baseContext, "My music")
        return myBinder
    }

    inner class MyBinder : Binder() {
        fun currentService(): MusicPlayerService {
            return this@MusicPlayerService
        }
    }

    fun showNotification(playPauseBtn: Int) {
        //Previous
        val prevIntent = Intent(
            baseContext,
            NotificationReceiver::class.java
        ).setAction(ApplicationClass.PREVIOUS)
        val prevPendingIntent = PendingIntent.getBroadcast(
            baseContext,
            0,
            prevIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        //Next
        val nextIntent = Intent(
            baseContext,
            NotificationReceiver::class.java
        ).setAction(ApplicationClass.NEXT)
        val nextPendingIntent = PendingIntent.getBroadcast(
            baseContext,
            0,
            nextIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        //Play
        val playIntent = Intent(
            baseContext,
            NotificationReceiver::class.java
        ).setAction(ApplicationClass.PLAY)
        val playPendingIntent = PendingIntent.getBroadcast(
            baseContext,
            0,
            playIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        //Exit
        val exitIntent = Intent(
            baseContext,
            NotificationReceiver::class.java
        ).setAction(ApplicationClass.EXIT)
        val exitPendingIntent = PendingIntent.getBroadcast(
            baseContext,
            0,
            exitIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(baseContext, ApplicationClass.CHANNEL_ID)
            .setContentTitle("Anh sai roi")
            .setContentText("MTP")
            .setSmallIcon(R.drawable.ic_library_music)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.ic_music_player_splash_screen
                )
            )
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .addAction(R.drawable.ic_skip_previous, "Previous", prevPendingIntent)
            .addAction(playPauseBtn, "Play", playPendingIntent)
            .addAction(R.drawable.ic_skip_next, "Next", nextPendingIntent)
            .addAction(R.drawable.ic_close, "Close", exitPendingIntent)
            .build()

        startForeground(13, notification)

    }

    private fun createMediaPlayer(link: String) {
        try {
            if (MusicPlayerFragment.musicPlayerService?.mediaPlayer == null) MusicPlayerFragment.musicPlayerService?.mediaPlayer =
                MediaPlayer()
            MusicPlayerFragment.musicPlayerService?.mediaPlayer?.reset()
            MusicPlayerFragment.musicPlayerService?.mediaPlayer?.setDataSource(
                Uri.parse(link).toString()
            )
            MusicPlayerFragment.musicPlayerService?.mediaPlayer?.prepare()
            MusicPlayerFragment.binding.btnPlayStopMusic.setImageResource(R.drawable.ic_pause)
            //seekbar
            MusicPlayerFragment.binding.seekbarTime.progress = 0
            MusicPlayerFragment.binding.seekbarTime.max =
                MusicPlayerFragment.musicPlayerService!!.mediaPlayer!!.duration

//            binding.timeTotal.text = formatSongDuration(listSong[1].duration)
            MusicPlayerFragment.binding.timeTotal.text = formatSongDuration(
                MusicPlayerFragment.musicPlayerService?.mediaPlayer?.duration.toString().toLong()
            )
            Log.d(
                MusicPlayerFragment.LOG,
                MusicPlayerFragment.musicPlayerService?.mediaPlayer?.duration.toString()
            )
            Log.d(
                MusicPlayerFragment.LOG,
                "ID: ${MusicPlayerFragment.musicPlayerService?.mediaPlayer!!.audioSessionId}"
            )
        } catch (ex: Exception) {

        }
    }


    fun seekBarSetup() {
        runnable = Runnable {
            MusicPlayerFragment.binding.timeReal.text=
                formatSongDuration((if (mediaPlayer?.currentPosition!=null) mediaPlayer?.currentPosition!!.toLong() else 0L))
            MusicPlayerFragment.binding.seekbarTime.progress = if(mediaPlayer?.currentPosition!=null) mediaPlayer?.currentPosition!! else 0
            Handler(Looper.getMainLooper()).postDelayed(runnable, 200)
        }
        Handler(Looper.getMainLooper()).postDelayed(runnable, 0)
    }
}