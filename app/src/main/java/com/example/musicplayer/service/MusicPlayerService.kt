package com.example.musicplayer.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.musicplayer.R
import com.example.musicplayer.fragment.MusicPlayerFragment
import com.example.musicplayer.model.Song
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


    @SuppressLint("UnspecifiedImmutableFlag")
    fun showNotification(playPauseBtn: Int) {
        when (MusicPlayerFragment.checkList) {
            1 -> {
                notification(
                    playPauseBtn,
                    MusicPlayerFragment.listRankSong,
                    MusicPlayerFragment.postion
                )
            }
            2 -> {
                notification(
                    playPauseBtn,
                    MusicPlayerFragment.listFavouriteSong,
                    MusicPlayerFragment.postion
                )
            }
            3 -> {
                notification(
                    playPauseBtn,
                    MusicPlayerFragment.listPlaylistSong,
                    MusicPlayerFragment.postion
                )
            }
            4 -> {
                notification(
                    playPauseBtn,
                    MusicPlayerFragment.listDevice,
                    MusicPlayerFragment.postion
                )
            }
            5 -> {
                notification(
                    playPauseBtn,
                    MusicPlayerFragment.listSearch,
                    MusicPlayerFragment.postion
                )
            }
        }


    }

    private fun notification(playPauseBtn: Int, listSong: List<Song>, pos: Int) {
        //Previous
        val prevIntent = Intent(
            baseContext,
            NotificationReceiver::class.java
        ).setAction(ApplicationClass.PREVIOUS)
        val prevPendingIntent = PendingIntent.getBroadcast(
            baseContext,
            0,
            prevIntent,
            PendingIntent.FLAG_IMMUTABLE
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
            PendingIntent.FLAG_IMMUTABLE
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
            PendingIntent.FLAG_IMMUTABLE
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
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(baseContext, ApplicationClass.CHANNEL_ID)
            .setContentTitle(listSong[pos].nameSong)
            .setContentText(listSong[pos].singer)
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

    fun createMediaPlayer() {
        try {
            if (MusicPlayerFragment.musicPlayerService?.mediaPlayer == null) MusicPlayerFragment.musicPlayerService?.mediaPlayer =
                MediaPlayer()
            MusicPlayerFragment.musicPlayerService?.mediaPlayer?.reset()
            MusicPlayerFragment.musicPlayerService?.mediaPlayer?.setDataSource(MusicPlayerFragment.listRankSong[MusicPlayerFragment.postion].urlSong)
            MusicPlayerFragment.musicPlayerService?.mediaPlayer?.prepare()

            MusicPlayerFragment.binding.btnPlayStopMusic.setImageResource(R.drawable.ic_pause)
            MusicPlayerFragment.musicPlayerService?.showNotification(R.drawable.ic_pause)

            //seekbar
            MusicPlayerFragment.binding.timeReal.text = formatSongDuration(
                MusicPlayerFragment.musicPlayerService?.mediaPlayer?.currentPosition.toString()
                    .toLong()
            )
            MusicPlayerFragment.binding.timeTotal.text =
                formatSongDuration(
                    MusicPlayerFragment.musicPlayerService?.mediaPlayer?.duration.toString()
                        .toLong()
                )
            MusicPlayerFragment.binding.seekbarTime.progress = 0
            MusicPlayerFragment.binding.seekbarTime.max =
                MusicPlayerFragment.musicPlayerService!!.mediaPlayer!!.duration

            MusicPlayerFragment.nowPlayingId =
                MusicPlayerFragment.listRankSong[MusicPlayerFragment.postion].idSong!!.toInt()
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
            MusicPlayerFragment.binding.timeReal.text =
                formatSongDuration((if (mediaPlayer?.currentPosition != null) mediaPlayer?.currentPosition!!.toLong() else 0L))
            MusicPlayerFragment.binding.seekbarTime.progress =
                if (mediaPlayer?.currentPosition != null) mediaPlayer?.currentPosition!! else 0
            Handler(Looper.getMainLooper()).postDelayed(runnable, 200)
        }
        Handler(Looper.getMainLooper()).postDelayed(runnable, 0)
    }
}