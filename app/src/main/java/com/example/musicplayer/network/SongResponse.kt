package com.example.musicplayer.network

import com.example.musicplayer.model.Song
import com.google.gson.annotations.SerializedName

data class SongResponse(
    @SerializedName("data")
    val songList:List<Song>

)