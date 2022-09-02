package com.example.musicplayer.model.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.musicplayer.model.Playlist
import com.example.musicplayer.model.Song

data class PlaylistWithSongs(
    @Embedded val playlist: Playlist,
    @Relation(
        parentColumn = "idPlaylist",
        entityColumn = "idSong",
        associateBy = Junction(SongPlaylistCrossRef::class)
    )
    val songs: List<Song>
)
