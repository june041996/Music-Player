package com.example.musicplayer.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.musicplayer.model.LoginModel
import com.example.musicplayer.repository.AuthenticationRepository


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
//    //đăng nhập thông tin
    fun register(mail: String,password: String){
        repository.requestRegister( mail, password)
    }



}