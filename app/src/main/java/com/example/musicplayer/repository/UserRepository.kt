package com.example.musicplayer.repository

import androidx.lifecycle.LiveData
import com.example.musicplayer.db.MusicDao
import com.example.musicplayer.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(val dao: MusicDao) {

    suspend fun getName(): List<User> = dao.getName()

    fun getUserById(id: Int): LiveData<User> = dao.getUsetById(id)

    fun checkLogin(username: String, password: String): User = dao.checkLogin(username, password)

}