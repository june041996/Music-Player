package com.example.musicplayer.repository

import com.example.musicplayer.db.MusicDao
import com.example.musicplayer.model.Favourite
import com.example.musicplayer.model.relation.FavouriteWithSongs

class FavouriteRepository(val dao: MusicDao) {

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