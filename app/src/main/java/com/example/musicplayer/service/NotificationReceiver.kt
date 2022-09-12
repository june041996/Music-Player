package com.example.musicplayer.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.musicplayer.R
import com.example.musicplayer.fragment.MusicPlayerFragment
import com.example.musicplayer.fragment.NowPlayingFragment
import com.example.musicplayer.utils.exitApp
import com.example.musicplayer.utils.setSongPosition
import kotlin.system.exitProcess

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(ctx: Context?, intent: Intent?) {
        when (intent?.action) {
            ApplicationClass.PREVIOUS -> prevNextSong(false, ctx!!)
            ApplicationClass.PLAY -> if (MusicPlayerFragment.isPlaying) pauseMusic() else playMusic()
            ApplicationClass.NEXT -> prevNextSong(true, ctx!!)
            ApplicationClass.EXIT -> {
                exitApp()
            }
        }
    }

    private fun playMusic() {
        MusicPlayerFragment.isPlaying = true
        MusicPlayerFragment.musicPlayerService?.mediaPlayer?.start()
        MusicPlayerFragment.musicPlayerService?.showNotification(
            R.drawable.ic_pause
        )
        MusicPlayerFragment.binding.btnPlayStopMusic.setImageResource(R.drawable.ic_pause)
        MusicPlayerFragment.binding.musicDisc.startAnimation(MusicPlayerFragment.animation)
        NowPlayingFragment.binding.btnPlayStopMusic.setImageResource(R.drawable.ic_pause)
    }

    private fun pauseMusic() {
        MusicPlayerFragment.isPlaying = false
        MusicPlayerFragment.musicPlayerService?.mediaPlayer?.pause()
        MusicPlayerFragment.musicPlayerService?.showNotification(
            R.drawable.ic_play_arrow
        )
        MusicPlayerFragment.binding.btnPlayStopMusic.setImageResource(R.drawable.ic_play_arrow)
        MusicPlayerFragment.binding.musicDisc.clearAnimation()
        NowPlayingFragment.binding.btnPlayStopMusic.setImageResource(R.drawable.ic_play_arrow)
    }

    private fun prevNextSong(increment: Boolean, context: Context) {
        setSongPosition(increment)

        MusicPlayerFragment.musicPlayerService!!.createMediaPlayer()

        Glide.with(context)
            .load(MusicPlayerFragment.listRankSong[MusicPlayerFragment.postion].urlImage)
            .placeholder(R.drawable.music).into(MusicPlayerFragment.binding.musicDisc)

        MusicPlayerFragment.binding.tvNameSong.text =
            MusicPlayerFragment.listRankSong[MusicPlayerFragment.postion].nameSong

        MusicPlayerFragment.binding.tvSinger.text =
            MusicPlayerFragment.listRankSong[MusicPlayerFragment.postion].singer

        Glide.with(context)
            .load(MusicPlayerFragment.listRankSong[MusicPlayerFragment.postion].urlImage)
            .placeholder(R.drawable.music).into(NowPlayingFragment.binding.sivImgSong)
        NowPlayingFragment.binding.tvNameSong.text =
            MusicPlayerFragment.listRankSong[MusicPlayerFragment.postion].nameSong

        NowPlayingFragment.binding.tvSinger.text =
            MusicPlayerFragment.listRankSong[MusicPlayerFragment.postion].singer
        playMusic()
    }
}