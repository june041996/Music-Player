package com.example.musicplayer.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.musicplayer.model.Song

class DiffSong(private val oldSongs: List<Song>, private val newSongs: List<Song>) :
    DiffUtil.Callback() {

    override fun getOldListSize() = oldSongs.size

    override fun getNewListSize() = newSongs.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldSongs[oldItemPosition].idSong == newSongs[newItemPosition].idSong
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldSongs === newSongs
    }
}