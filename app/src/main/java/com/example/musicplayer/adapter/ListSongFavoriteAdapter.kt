package com.example.musicplayer.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.ListMusicFavoriteBinding
import com.example.musicplayer.model.Song

class ListSongFavoriteAdapter :
    RecyclerView.Adapter<ListSongFavoriteAdapter.ViewHolder>() {
    private val mFavofite = arrayListOf<Song>()
    private lateinit var listener: OnItemClickListener


    fun submitDataFavorite(temp: List<Song>) {
        val diff = DiffUtil.calculateDiff(DiffSong(mFavofite, temp))
        mFavofite.clear()
        mFavofite.addAll(temp)
        diff.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(
        onItemClickListener: OnItemClickListener
    ) {
        listener = onItemClickListener
    }

    class ViewHolder(private val binding: ListMusicFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(f: Song) {
            binding.favorite = f
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListMusicFavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mFavofite[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount() = mFavofite.size
}
