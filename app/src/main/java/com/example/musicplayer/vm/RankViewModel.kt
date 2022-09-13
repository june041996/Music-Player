package com.example.musicplayer.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.example.musicplayer.repository.RankRepository
import com.example.musicplayer.utils.Resource
import kotlinx.coroutines.Dispatchers

class RankViewModel(application: Application) : AndroidViewModel(application) {
    private val rankRepository = RankRepository(getApplication<Application>().applicationContext)

    companion object {
        private const val LOG: String = "TCR"
    }

    fun getSongsOnlineFromDB() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val data = rankRepository.getSongsOnline()
            emit(Resource.success(data = data))
        } catch (ex: Exception) {
            emit(Resource.error(data = null, message = ex.message ?: "Error !!"))
        }
    }
}