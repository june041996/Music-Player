package com.example.musicplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.adapter.rank.ItemViewOnClick
import com.example.musicplayer.databinding.ItemRankMusicBinding
import com.example.musicplayer.model.Song
import com.example.musicplayer.utils.Resource

class ListRankAdapter : RecyclerView.Adapter<ListRankAdapter.ViewHolder>() {

    private val listRank = arrayListOf<Song>()
    private lateinit var itemViewOnClick: ItemViewOnClick

    fun submitDataRank(temp: List<Song>) {
        val diff = DiffUtil.calculateDiff(DiffSong(listRank, temp))
        listRank.clear()
        listRank.addAll(temp)
        diff.dispatchUpdatesTo(this)
    }

    fun setItemViewOnClick(itemViewOnClick: ItemViewOnClick) {
        this.itemViewOnClick = itemViewOnClick
    }

    inner class ViewHolder(private val binding: ItemRankMusicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.song = song
            binding.executePendingBindings()
        }

        init {
            binding.root.setOnClickListener {
                itemViewOnClick.onClick(listRank[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRankMusicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listRank[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount()= listRank.size

}