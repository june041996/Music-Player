package com.example.musicplayer.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.ItemAddToPlaylistBinding
import com.example.musicplayer.model.Playlist

class AddToPlaylistAdapter() : RecyclerView.Adapter<AddToPlaylistAdapter.ViewHolder>() {
    private var playlists = arrayListOf<Playlist>()
    private lateinit var listener: OnItemClickListener

    fun submitData(temp: ArrayList<Playlist>) {
        val diff = DiffUtil.calculateDiff(DiffPlaylist(playlists, temp))
        playlists.clear()
        playlists.addAll(temp)
        diff.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(
        onItemClickListener: OnItemClickListener
    ) {
        listener = onItemClickListener
    }

    class ViewHolder(
        private var binding: ItemAddToPlaylistBinding,
        val listener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener() {
                listener.onItemClick(absoluteAdapterPosition)
            }
        }

        fun bind(p: Playlist) {
            binding.playlist = p
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAddToPlaylistBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        playlists[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount() = playlists.size
}