package com.example.musicplayer.vm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.db.MusicDatabase
import com.example.musicplayer.model.Playlist
import com.example.musicplayer.model.Song
import com.example.musicplayer.model.User
import com.example.musicplayer.repository.PlaylistRepository
import com.example.musicplayer.repository.UserRepository
import com.example.musicplayer.utils.Contanst
import kotlinx.coroutines.launch

class UserViewMode(app: Application) : AndroidViewModel(app) {
    private val dao = MusicDatabase.getInstance(getApplication()).songDao()
    val userRepository = UserRepository(getApplication<Application>().applicationContext)
//    private val SHARED_PREFS = "shared_prefs"
//    private var sharedpreferences: SharedPreferences =
//        getApplication<Application>().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
//    private val id = sharedpreferences.getInt("id", 0)
//    private val name = sharedpreferences.getString("username", null)

    var _user = MutableLiveData<ArrayList<User>>()
    val user: LiveData<ArrayList<User>>
        get() = getName()
    val list = arrayListOf<User>()
    fun getName(): MutableLiveData<ArrayList<User>> {
        viewModelScope.launch {
            list.clear()
            list.addAll(userRepository.getName())
            _user.value = list
        }
        return _user
    }


}