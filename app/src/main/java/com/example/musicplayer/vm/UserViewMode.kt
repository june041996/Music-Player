package com.example.musicplayer.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.db.MusicDatabase
import com.example.musicplayer.model.User
import kotlinx.coroutines.launch

class UserViewMode(app: Application) : AndroidViewModel(app) {
    private val dao = MusicDatabase.getInstance(getApplication()).songDao()

    fun insertUser(user: User) {
        viewModelScope.launch {
            dao.insertUser(user)
        }
    }
}