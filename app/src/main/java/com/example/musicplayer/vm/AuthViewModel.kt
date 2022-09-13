package com.example.musicplayer.vm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.db.MusicDatabase
import com.example.musicplayer.model.LoginModel
import com.example.musicplayer.model.User
import com.example.musicplayer.repository.AuthenticationRepository
import kotlinx.coroutines.launch


class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AuthenticationRepository(application)
    val isSuccessful: LiveData<Boolean>
    val dao = MusicDatabase.getInstance(getApplication()).songDao()
    private val SHARED_PREFS = "shared_prefs"
    private var sharedpreferences: SharedPreferences =
        getApplication<Application>().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    private val id = sharedpreferences.getInt("id", 0)
    private val name = sharedpreferences.getString("username", null)

    val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun getUser(email: String) {
        viewModelScope.launch {
            _user.value = dao.getUser(email)
        }
    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            dao.insertUser(user)
        }
    }

    init {
        isSuccessful = repository.isSuccessful
    }

    //request login to filebase
    fun requestLogin(mail: String, password: String) {
        repository.requestLogin(mail, password)
    }

    //lay thong tin dang nhap
    fun getLoginInfo(): LoginModel {
        return repository.getLoginInfo()
    }

    //đăng nhập thông tin
    fun register(mail: String, password: String) {
        repository.requestRegister(mail, password)
    }


}