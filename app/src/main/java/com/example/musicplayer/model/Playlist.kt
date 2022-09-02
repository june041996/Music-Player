package com.example.musicplayer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_playlist")
data class Playlist(
    @PrimaryKey(autoGenerate = true)
    val idPlaylist: Int?,
    val idUserCreator: Int,
    val name: String,
    val dateTimeCreate: String
)