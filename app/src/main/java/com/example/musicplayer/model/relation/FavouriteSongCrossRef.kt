package com.example.musicplayer.model.relation

import androidx.room.Entity


@Entity(primaryKeys = ["idFavourite", "idSong"])
data class FavouriteSongCrossRef(
    val idFavourite: Int,
    val idSong: Int
)
