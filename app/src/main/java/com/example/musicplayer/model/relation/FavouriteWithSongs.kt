package com.example.musicplayer.model.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.musicplayer.model.Favourite
import com.example.musicplayer.model.Song


data class FavouriteWithSongs(
    @Embedded val favourite: Favourite,
    @Relation(
        parentColumn = "idFavourite",
        entityColumn = "idSong",
        associateBy = Junction(FavouriteSongCrossRef::class)
    )
    val songs: List<Song>
)