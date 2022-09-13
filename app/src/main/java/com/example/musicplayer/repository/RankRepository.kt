package com.example.musicplayer.repository

import androidx.lifecycle.LiveData
import com.example.musicplayer.db.MusicDao
import com.example.musicplayer.model.Song

class RankRepository(val dao: MusicDao) {

    fun getSongsOnline(): LiveData<List<Song>> = dao.getSongsOnline(0)

}