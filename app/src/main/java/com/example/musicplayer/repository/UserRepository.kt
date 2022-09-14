package com.example.musicplayer.repository

import androidx.lifecycle.LiveData
import com.example.musicplayer.db.MusicDao
import com.example.musicplayer.model.User


class UserRepository(val dao: MusicDao) {
    // var dao: MusicDao = MusicDatabase.getInstance(context).songDao()

    suspend fun getName(): List<User> {
        return dao.getName()
    }

    fun getUserById(id: Int): LiveData<User> {
        return dao.getUsetById(id)
    }

}