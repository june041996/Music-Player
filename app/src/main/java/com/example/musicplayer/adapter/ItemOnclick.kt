package com.example.musicplayer.adapter

import com.example.musicplayer.model.Song

interface ItemOnclick {
    fun onClick(song: Song)
}