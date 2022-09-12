package com.example.musicplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.ListMusicPlaylistBinding
import com.example.musicplayer.model.Song

class SongDiff(
    val oldSong: List<Song>,
    val newSong: List<Song>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldSong.size
    override fun getNewListSize() = newSong.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldSong[oldItemPosition].idSong == newSong[oldItemPosition].idSong
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldSong === newSong
    }

}

class ListSongPlaylistAdapter :
    RecyclerView.Adapter<ListSongPlaylistAdapter.ViewHolder>() {
    private val song = arrayListOf<Song>()

    fun submitData(temp: List<Song>) {
        val diff = DiffUtil.calculateDiff(SongDiff(song, temp))
        song.clear()
        song.addAll(temp)
        diff.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: ListMusicPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(s: Song) {
            binding.song = s
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListMusicPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        song[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount() = song.size
}