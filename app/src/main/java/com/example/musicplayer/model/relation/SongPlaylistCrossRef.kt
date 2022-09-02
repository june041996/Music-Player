package com.example.musicplayer.model.relation

import androidx.room.Entity

@Entity(primaryKeys = ["idSong", "idPlaylist"])
data class SongPlaylistCrossRef(
    val idSong: Int,
    val idPlaylist: Int
)