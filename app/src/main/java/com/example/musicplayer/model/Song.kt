package com.example.musicplayer.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tb_song")
data class Song(
    @PrimaryKey(autoGenerate = true)
    val idSong: Int?,
    val titleName: String,
    val urlImage: String,
    val urlSong: String,
    val category: String,
    val artist: String,
    val singer: String,
    val album: String,
    val duration: String,
    val views: Int
) : Serializable