package com.example.musicplayer.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.musicplayer.db.MusicDao
import com.example.musicplayer.db.MusicDatabase
import com.example.musicplayer.model.Song

class RankRepository(val context: Context) {
    var dao: MusicDao = MusicDatabase.getInstance(context).songDao()

    fun getSongsOnline(): LiveData<List<Song>> = dao.getSongsOnline(0)

}