package com.example.musicplayer.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicplayer.model.LoginModel
import com.example.musicplayer.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser


class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repository  = AuthenticationRepository(application)
    val isSuccessful : LiveData<Boolean>

    init {
        isSuccessful = repository.isSuccessful
    }

    //request login to filebase
    fun requestLogin(mail: String, password: String) {
        repository.requestLogin(mail, password)
    }

    //lay thong tin dang nhap
    fun getLoginInfo() : LoginModel {
        return repository.getLoginInfo()
    }

}