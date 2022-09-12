package com.example.musicplayer.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.musicplayer.model.Favourite

class DiffFavorite(val oldFavorite: List<Favourite>, val newFavourite: List<Favourite>) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldFavorite.size
    override fun getNewListSize() = newFavourite.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavorite[oldItemPosition].idSong == newFavourite[newItemPosition].idSong
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavorite === newFavourite
    }

}