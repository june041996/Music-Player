package com.example.musicplayer.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.musicplayer.model.Favourite
import com.example.musicplayer.model.Playlist
import com.example.musicplayer.model.Song
import com.example.musicplayer.model.User
import com.example.musicplayer.model.relation.*


@Dao
interface MusicDao {
    //INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(song: Song)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: Playlist)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: Favourite)

    //insert song_playlist
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongPlaylistCrossRef(songPlaylistCrossRef: SongPlaylistCrossRef)

    //insert song_favourite
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteSongCrossRef(favouriteSongCrossRef: FavouriteSongCrossRef)


    //UPDATE
    @Update
    suspend fun updateSong(song: Song)

    //DELETE
    @Query("DELETE FROM tb_song WHERE idSong=:id")
    suspend fun deleteSong(id: Int)

    //QUERY
    @Query("SELECT * FROM tb_song")
    fun getAllSongs(): LiveData<List<Song>>

    //get all local song with isOffline = true
    @Query("SELECT * FROM tb_song WHERE isOffline=:status ")
    suspend fun getLocalSongs(status: Boolean): List<Song>

    //get list song of playlist
    @Transaction
    @Query("SELECT * FROM tb_playlist WHERE idPlaylist=:id")
    suspend fun getSongsOfPlaylist(id: Int): List<PlaylistWithSongs>

    //get user playlist song
    @Transaction
    @Query("SELECT * FROM tb_user WHERE idUser=:id")
    suspend fun getUserWithPlaylistsAndSongs(id: Int): List<UserWithPlaylistsAndSongs>

    //get list song of favourite
    @Transaction
    @Query("SELECT * FROM tb_favourite WHERE idFavourite=:id")
    suspend fun getSongsOfFavourite(id: Int): List<FavouriteWithSongs>

    //get user  favourite song
    @Transaction
    @Query("SELECT * FROM tb_user WHERE idUser=:id")
    suspend fun getUserWithFavouriteAndSongs(id: Int): List<UserWithFavouriteAndSongs>
}