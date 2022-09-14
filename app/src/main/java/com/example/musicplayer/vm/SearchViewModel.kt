package com.example.musicplayer.vm

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.example.musicplayer.db.MusicDao
import com.example.musicplayer.repository.FavouriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val dao: MusicDao,
    val prefs: SharedPreferences,
    app: Application
) : AndroidViewModel(app) {
    val favouriteRepository = FavouriteRepository(dao)
    private val id = prefs.getInt("id", 0)
    private val name = prefs.getString("username", null)
    val searchQuery = MutableStateFlow("")

    private val songsFlow = searchQuery.flatMapLatest {
        dao.getSongs(it, true)
    }

    val tasks = songsFlow.asLiveData()

    private val playlistFlow = searchQuery.flatMapLatest {
        dao.getPlaylists(it, id)
    }

    val playlists = playlistFlow.asLiveData()

    private val allSongsFlow = searchQuery.flatMapLatest {
        dao.getFlowAllSongs(it)
    }

    val allSongs = allSongsFlow.asLiveData()

}