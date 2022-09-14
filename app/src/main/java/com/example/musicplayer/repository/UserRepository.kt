package com.example.musicplayer.repository

import com.example.musicplayer.db.MusicDao
import com.example.musicplayer.model.User


class UserRepository(val dao: MusicDao) {
    // var dao: MusicDao = MusicDatabase.getInstance(context).songDao()

    suspend fun getName(): List<User> {
        return dao.getName()
    }


}