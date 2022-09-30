package com.example.musicplayer.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.db.MusicDao
import com.example.musicplayer.db.MusicDatabase
import com.example.musicplayer.model.LoginModel
import com.example.musicplayer.model.User
import com.example.musicplayer.repository.AuthenticationRepository
import com.example.musicplayer.repository.UserRepository
import kotlinx.coroutines.launch


class AuthViewModel(application: Application) :
    AndroidViewModel(application) {
    val dao: MusicDao = MusicDatabase.getInstance(getApplication()).songDao()
    private val repository = AuthenticationRepository(application)
    private val userRepository: UserRepository =
        UserRepository(MusicDatabase.getInstance(application).songDao())

    val isSuccessful: LiveData<Boolean>


    val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    suspend fun getUser(email: String, password: String): User? {

        return dao.getUser(email, password)

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
    fun requestLogin(mail: String, password: String): User {
        //repository.requestLogin(mail, password)
        return userRepository.checkLogin(mail, password)
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