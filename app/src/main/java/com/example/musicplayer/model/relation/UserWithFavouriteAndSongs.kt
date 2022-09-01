package com.example.musicplayer.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.musicplayer.model.Favourite
import com.example.musicplayer.model.User

data class UserWithFavouriteAndSongs(
    @Embedded val user: User,
    @Relation(
        entity = Favourite::class,
        parentColumn = "idUser",
        entityColumn = "idUser",
    )
    val favourites: List<FavouriteWithSongs>
)