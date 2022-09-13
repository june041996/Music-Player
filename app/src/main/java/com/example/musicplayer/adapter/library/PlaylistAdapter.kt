package com.example.musicplayer.adapter.library


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.adapter.DiffPlaylist
import com.example.musicplayer.databinding.ItemPlaylistBinding
import com.example.musicplayer.model.Playlist

class PlaylistAdapter() : RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {
    private var playlists = arrayListOf<Playlist>()
    private lateinit var listener: OnItemClickListener
    private lateinit var mButton: OnItemButtonClickListener


    fun submitData(temp: ArrayList<Playlist>) {
        val diff = DiffUtil.calculateDiff(DiffPlaylist(playlists, temp))
        playlists.clear()
        playlists.addAll(temp)
        diff.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(
        onItemClickListener: OnItemClickListener,
        onItemButtonClickListener: OnItemButtonClickListener
    ) {
        listener = onItemClickListener
        mButton = onItemButtonClickListener
    }

    class ViewHolder(
        private var binding: ItemPlaylistBinding,
        val listener: OnItemClickListener,
        val mButton: OnItemButtonClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener() {
                listener.onItemClick(absoluteAdapterPosition)
            }
            binding.imgMenu.setOnClickListener() {
                mButton.onItemClick(absoluteAdapterPosition, it)
            }
        }

        fun bind(p: Playlist) {
            binding.playlist = p
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPlaylistBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), listener, mButton
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        playlists[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount() = playlists.size
}