package com.example.musicplayer.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.musicplayer.model.Playlist
import com.example.musicplayer.model.User

data class UserWithPlaylistsAndSongs(
    @Embedded val user: User,
    @Relation(
        entity = Playlist::class,
        parentColumn = "idUser",
        entityColumn = "idUserCreator"

    )
    val playlists: List<PlaylistWithSongs>
)
