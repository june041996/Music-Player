package com.example.musicplayer.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.musicplayer.model.Playlist

class DiffPlaylist(val oldPlaylists: List<Playlist>, val newPlaylists: List<Playlist>) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldPlaylists.size
    override fun getNewListSize() = newPlaylists.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldPlaylists[oldItemPosition].idPlaylist == newPlaylists[newItemPosition].idPlaylist
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldPlaylists === newPlaylists
    }
}