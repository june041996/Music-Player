package com.example.musicplayer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_favourite")
data class Favourite(
    @PrimaryKey
    val idFavourite: Int,
    val idUser: Int
)

