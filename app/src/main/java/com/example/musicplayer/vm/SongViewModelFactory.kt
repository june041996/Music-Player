package com.example.musicplayer.vm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//Dùng để truyền ctx khi khởi tạo viewmodel (dùng Dao)
class SongViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    //create ->sẽ tạo viewmodel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SongViewModel::class.java)) {
            return SongViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown to create your view model")
    }
}