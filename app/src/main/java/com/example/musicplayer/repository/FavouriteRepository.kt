package com.example.musicplayer.repository

import com.example.musicplayer.db.MusicDao
import com.example.musicplayer.model.Favourite
import com.example.musicplayer.model.relation.FavouriteWithSongs
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteRepository @Inject constructor(val dao: MusicDao) {

    suspend fun insertFavourite(favourite: Favourite) = dao.insertFavourite(favourite)


    suspend fun deleteFavouriteSong(idUser: Int, idSong: Int) =
        dao.deleteFavouriteSong(idUser, idSong)


    suspend fun getSongsOfFavourite(idUser: Int): List<FavouriteWithSongs> =
        dao.getSongsOfFavourite(idUser)


    suspend fun getFavouriteSong(idUser: Int, idSong: Int): Favourite =
        dao.getFavouriteSong(idUser, idSong)

}