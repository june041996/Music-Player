package com.example.musicplayer.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import com.example.musicplayer.R
import com.example.musicplayer.fragment.MusicPlayerFragment
import com.example.musicplayer.fragment.NowPlayingFragment
import com.example.musicplayer.model.Song
import com.example.musicplayer.utils.exitApp
import com.example.musicplayer.utils.setSongPosition

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
        when (MusicPlayerFragment.checkList) {
            1 -> {
                setUpSong(context, MusicPlayerFragment.listRankSong, MusicPlayerFragment.postion)
            }
            2 -> {
                setUpSong(
                    context,
                    MusicPlayerFragment.listFavouriteSong,
                    MusicPlayerFragment.postion
                )
            }
            3 -> {
                setUpSong(
                    context,
                    MusicPlayerFragment.listPlaylistSong,
                    MusicPlayerFragment.postion
                )
            }
            4 -> {
                setUpSong(
                    context,
                    MusicPlayerFragment.listDevice,
                    MusicPlayerFragment.postion
                )
            }
            5->{
                setUpSong(
                    context,
                    MusicPlayerFragment.listSearch,
                    MusicPlayerFragment.postion
                )
            }
        }

        playMusic()
    }

    private fun setUpSong(context: Context, listSong: List<Song>, pos: Int) {
        Glide.with(context)
            .load(listSong[pos].urlImage)
            .placeholder(R.drawable.music).into(MusicPlayerFragment.binding.musicDisc)

        MusicPlayerFragment.binding.tvNameSong.text = listSong[pos].nameSong

        MusicPlayerFragment.binding.tvSinger.text = listSong[pos].singer

        Glide.with(context)
            .load(listSong[pos].urlImage)
            .placeholder(R.drawable.music).into(NowPlayingFragment.binding.sivImgSong)
        NowPlayingFragment.binding.tvNameSong.text = listSong[pos].nameSong

        NowPlayingFragment.binding.tvSinger.text = listSong[pos].singer
    }
}