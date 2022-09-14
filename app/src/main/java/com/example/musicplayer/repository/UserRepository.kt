package com.example.musicplayer.repository


class UserRepository(val dao: MusicDao) {
    // var dao: MusicDao = MusicDatabase.getInstance(context).songDao()

    suspend fun getName(): List<User> {
        return dao.getName()
    }


}