package com.example.musicplayer.adapter.rank

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.adapter.DiffSong
import com.example.musicplayer.databinding.ItemRankMusicBinding
import com.example.musicplayer.model.Song

class RankAdapter : RecyclerView.Adapter<RankAdapter.RankViewHolder>() {

    companion object {
        var isFavourite = false
    }

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


    inner class RankViewHolder(val binding: ItemRankMusicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.song = song
            binding.tvNameSong.isSelected = true
            binding.tvSinger.isSelected = true

            binding.executePendingBindings()
        }

        init {
            binding.root.setOnClickListener {
                itemViewOnClick.onClick(
                    listSong[bindingAdapterPosition],
                    pos = bindingAdapterPosition
                )
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        listSong[position].let {
            with(holder) {
                bind(it)
                val pos = position + 1
                when (position) {
                    0 -> {
                        binding.tvCount.text = pos.toString()
                        binding.tvCount.setTextColor(Color.BLUE)
                    }
                    1 -> {
                        binding.tvCount.text = pos.toString()
                        binding.tvCount.setTextColor(Color.GREEN)
                    }
                    2 -> {
                        binding.tvCount.text = pos.toString()
                        binding.tvCount.setTextColor(Color.RED)
                    }
                    else -> {
                        binding.tvCount.text = pos.toString()
                        binding.tvCount.setTextColor(Color.GRAY)
                    }
                }
                binding.favourite.setOnClickListener {
                    if (isFavourite) {
                        isFavourite = false
                        binding.favourite.setImageResource(R.drawable.ic_favorite)
                        val song = listSong[position]

                    } else {
                        isFavourite = true
                        binding.favourite.setImageResource(R.drawable.ic_unfavorite)
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return listSong.size
    }
}