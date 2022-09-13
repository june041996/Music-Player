package com.example.musicplayer.repository

import android.content.Context
import com.example.musicplayer.db.MusicDatabase
import com.example.musicplayer.model.Favourite
import com.example.musicplayer.model.relation.FavouriteWithSongs

class FavouriteRepository(val context: Context) {
    val dao = MusicDatabase.getInstance(context).songDao()

    suspend fun insertFavourite(favourite: Favourite) {
        dao.insertFavourite(favourite)
    }

    suspend fun deleteFavouriteSong(idUser: Int, idSong: Int) {
        dao.deleteFavouriteSong(idUser, idSong)
    }

    suspend fun getSongsOfFavourite(idUser: Int): List<FavouriteWithSongs> {
        return dao.getSongsOfFavourite(idUser)
    }

    suspend fun getFavouriteSong(idUser: Int, idSong: Int): Favourite {
        return dao.getFavouriteSong(idUser, idSong)
    }
}