package com.example.musicplayer.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.musicplayer.R
import com.example.musicplayer.fragment.MusicPlayerFragment
import kotlin.system.exitProcess

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(ctx: Context?, intent: Intent?) {
        when (intent?.action) {
            ApplicationClass.PREVIOUS -> Toast.makeText(ctx, "Previous click", Toast.LENGTH_SHORT)
                .show()
            ApplicationClass.PLAY -> if (MusicPlayerFragment.isPlaying) pauseMusic() else playMusic()
            ApplicationClass.NEXT -> Toast.makeText(ctx, "Next click", Toast.LENGTH_SHORT).show()
            ApplicationClass.EXIT -> {
                MusicPlayerFragment.musicPlayerService?.stopForeground(true)
                MusicPlayerFragment.musicPlayerService = null
                exitProcess(1)
            }
        }
    }

    private fun playMusic() {
        MusicPlayerFragment.isPlaying = true
        MusicPlayerFragment.musicPlayerService?.mediaPlayer?.start()
        MusicPlayerFragment.musicPlayerService?.showNotification(R.drawable.ic_pause)
        MusicPlayerFragment.binding.btnPlayStopMusic.setImageResource(R.drawable.ic_pause)
        MusicPlayerFragment.binding.musicDisc.startAnimation(MusicPlayerFragment.animation)
    }

    private fun pauseMusic() {
        MusicPlayerFragment.isPlaying = false
        MusicPlayerFragment.musicPlayerService?.mediaPlayer?.pause()
        MusicPlayerFragment.musicPlayerService?.showNotification(R.drawable.ic_play_arrow)
        MusicPlayerFragment.binding.btnPlayStopMusic.setImageResource(R.drawable.ic_play_arrow)
        MusicPlayerFragment.binding.musicDisc.clearAnimation()
    }
}