package com.example.musicplayer.vm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.db.MusicDatabase
import com.example.musicplayer.model.User
import kotlinx.coroutines.launch

class UserViewMode(app: Application) : AndroidViewModel(app) {
    private val dao = MusicDatabase.getInstance(getApplication()).songDao()
    private val SHARED_PREFS = "shared_prefs"
    private var sharedpreferences: SharedPreferences =
        getApplication<Application>().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    private val id = sharedpreferences.getInt("id", 0)
    private val name = sharedpreferences.getString("username", null)


    fun insertUser(user: User) {
        viewModelScope.launch {
            dao.insertUser(user)
        }
    }
}