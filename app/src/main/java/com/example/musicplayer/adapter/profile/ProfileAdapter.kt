package com.example.musicplayer.adapter.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.example.musicplayer.databinding.ItemProfileBinding

import com.example.musicplayer.model.User


class DiffProfile(val oldProfile: List<User>, val newProfile: List<User>) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldProfile.size
    override fun getNewListSize() = newProfile.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldProfile[oldItemPosition].idUser == newProfile[newItemPosition].idUser
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldProfile === newProfile
    }
}

class ProfileAdapter : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    private val mProfile = arrayListOf<User>()
    private lateinit var itemProfileOnClick: ItemProfileOnClick

    fun submitData(temp: List<User>) {
        val diff = DiffUtil.calculateDiff(DiffProfile(mProfile, temp))
        mProfile.clear()
        mProfile.addAll(temp)
        diff.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(itemProfileOnClick: ItemProfileOnClick ) {
        this.itemProfileOnClick = itemProfileOnClick
    }

    inner class ViewHolder(private val binding: ItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(u: User) {
            binding.user = u
            binding.executePendingBindings()
        }

        init {
            binding.root.setOnClickListener {
                itemProfileOnClick.onClick(mProfile[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                ItemProfileBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mProfile[position].let {
            holder.bind(it)
        }
    }

    override fun getItemCount() = mProfile.size


}
