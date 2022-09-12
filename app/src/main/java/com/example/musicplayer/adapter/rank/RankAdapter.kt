package com.example.musicplayer.adapter.rank

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.adapter.DiffSong
import com.example.musicplayer.databinding.ItemRankMusicBinding
import com.example.musicplayer.model.Song

class RankAdapter : RecyclerView.Adapter<RankAdapter.RankViewHolder>() {
    private val listSong = mutableListOf<Song>()
    private lateinit var itemViewOnClick: ItemViewOnClick

    fun submitData(temp: List<Song>) {
        val diff = DiffUtil.calculateDiff(DiffSong(listSong, temp))
        listSong.clear()
        listSong.addAll(temp)
        diff.dispatchUpdatesTo(this)
    }

    fun setItemViewOnClick(itemViewOnClick: ItemViewOnClick) {
        this.itemViewOnClick = itemViewOnClick
    }

    inner class RankViewHolder(private val binding: ItemRankMusicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.song = song
//            for (i in 1..5){
//                when(i){
//                    1-> binding.tvCount.text = "1"
//                }
//            }
            binding.executePendingBindings()
        }

        init {
            binding.root.setOnClickListener {
                itemViewOnClick.onClick(listSong[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        return RankViewHolder(
            ItemRankMusicBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        listSong[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return listSong.size
    }
}