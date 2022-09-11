package com.example.musicplayer.model

import androidx.room.Entity

@Entity(tableName = "tb_favourite", primaryKeys = ["idUser", "idSong"])
data class Favourite(
    val idUser: Int,
    val idSong: Int
)

