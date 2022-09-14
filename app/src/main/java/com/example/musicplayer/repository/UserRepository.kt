package com.example.musicplayer.repository

import android.content.Context
import com.example.musicplayer.db.MusicDao
import com.example.musicplayer.db.MusicDatabase
import com.example.musicplayer.model.User

class UserRepository(val context: Context) {
    var dao: MusicDao = MusicDatabase.getInstance(context).songDao()


    suspend fun getName(): List<User> {
        return dao.getName()
    }


}