package com.example.musicplayer.vm

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.db.MusicDao
import com.example.musicplayer.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewMode @Inject constructor(
    val dao: MusicDao,
    val prefs: SharedPreferences,
    app: Application
) : AndroidViewModel(app) {

    private val id = prefs.getInt("id", 0)
    private val name = prefs.getString("username", null)


    fun insertUser(user: User) {
        viewModelScope.launch {
            dao.insertUser(user)
        }
    }
}