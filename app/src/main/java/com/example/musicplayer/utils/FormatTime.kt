package com.example.musicplayer.utils

import java.util.concurrent.TimeUnit

fun formatSongDuration(duration: Long): String {
//    val minutes = duration.subSequence(0, 2).toString().toLong()
//    val seconds = duration.subSequence(3, 5).toString().toLong()

    val minutes = TimeUnit.MINUTES.convert(duration,TimeUnit.MILLISECONDS)
    val seconds = TimeUnit.SECONDS.convert(duration,TimeUnit.MILLISECONDS) - minutes*TimeUnit.SECONDS.convert(1,TimeUnit.MINUTES)
    return String.format("%02d:%02d", minutes, seconds)
}