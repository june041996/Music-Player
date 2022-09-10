package com.example.musicplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.ItemOnDeviceSongBinding
import com.example.musicplayer.model.Song

class OnDeviceAdapter() : RecyclerView.Adapter<OnDeviceAdapter.ViewHolder>() {
    private var songs = arrayListOf<Song>()
    private lateinit var listener: OnItemClickListener

    fun submitData(temp: ArrayList<Song>) {
        val diff = DiffUtil.calculateDiff(DiffSong(songs, temp))
        songs.clear()
        songs.addAll(temp)
        diff.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        listener = onItemClickListener
    }

    class ViewHolder(
        private var binding: ItemOnDeviceSongBinding,
        val listener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener() {
                listener.onItemClick(absoluteAdapterPosition)
            }
        }

        fun bind(s: Song) {
            binding.song = s
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOnDeviceSongBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        songs[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount() = songs.size
}