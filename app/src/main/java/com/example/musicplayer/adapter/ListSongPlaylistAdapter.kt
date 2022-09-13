package com.example.musicplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.adapter.rank.ItemViewOnClick
import com.example.musicplayer.databinding.ListMusicPlaylistBinding
import com.example.musicplayer.model.Playlist


class ListSongPlaylistAdapter :
    RecyclerView.Adapter<ListSongPlaylistAdapter.ViewHolder>() {
    private val song = arrayListOf<Playlist>()
    private lateinit var listener: ItemOnclick
    fun submitData(temp: List<Playlist>) {
        val diff = DiffUtil.calculateDiff(DiffPlaylist(song, temp))
        song.clear()
        song.addAll(temp)
        diff.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(onItemClickListener: ItemOnclick){
        listener = onItemClickListener
    }

   inner class ViewHolder(private val binding: ListMusicPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(s: Playlist) {
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