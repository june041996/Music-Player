package com.example.musicplayer.utils

import com.example.musicplayer.fragment.MusicPlayerFragment
import kotlin.system.exitProcess

fun setSongPosition(increment: Boolean) {
    //Nếu không repeat thì next hoạc previous
    if (!MusicPlayerFragment.repeat) {
        if (increment) {
            //Next tại cuối-> Bài đầu
            if (MusicPlayerFragment.listRankSong.size - 1 == MusicPlayerFragment.postion)
                MusicPlayerFragment.postion = 0
            else
                ++MusicPlayerFragment.postion

        } else {
            //Pre tại 0 -> Bài cuối
            if (0 == MusicPlayerFragment.postion)
                MusicPlayerFragment.postion = MusicPlayerFragment.listRankSong.size - 1
            else
                --MusicPlayerFragment.postion
        }
    }
}

fun exitApp(){
    if (MusicPlayerFragment.musicPlayerService != null) {
        MusicPlayerFragment.musicPlayerService!!.stopForeground(true)
        MusicPlayerFragment.musicPlayerService!!.mediaPlayer!!.release()
        MusicPlayerFragment.musicPlayerService = null
        exitProcess(1)
    }
}

