package com.example.musicplayer.model.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.musicplayer.model.Favourite
import com.example.musicplayer.model.Song
import com.example.musicplayer.model.User


data class FavouriteWithSongs(
    @Embedded val user: User,
    @Relation(
        parentColumn = "idUser",
        entityColumn = "idSong",
        associateBy = Junction(Favourite::class)
    )
    val songs: List<Song>
)