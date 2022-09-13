package com.example.musicplayer.fragment.library

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapter.library.FavouriteSongAdapter
import com.example.musicplayer.adapter.library.OnItemButtonClickListener
import com.example.musicplayer.adapter.library.OnItemClickListener
import com.example.musicplayer.databinding.FragmentFavouriteBinding
import com.example.musicplayer.model.Song
import com.example.musicplayer.utils.Contanst
import com.example.musicplayer.utils.CustomDialog
import com.example.musicplayer.vm.FavouriteViewModel


class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private var songs = arrayListOf<Song>()
    private val favouriteViewModel: FavouriteViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favourite = favouriteViewModel
        binding.lifecycleOwner = this


        val adapter = FavouriteSongAdapter()
        binding.rvSongs.adapter = adapter
        binding.rvSongs.layoutManager = LinearLayoutManager(context)
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d(Contanst.TAG, "item: ${songs[position].nameSong}")

            }

        }, object : OnItemButtonClickListener {
            override fun onItemClick(position: Int, view: View) {
                //remove favourite
                CustomDialog(requireContext()).createConfirmDialog(object :
                    CustomDialog.OnSubmitBtnClick {
                    override fun onClick(name: String) {
                        favouriteViewModel.removeFavouriteSong(songs[position])
                        songs.remove(songs[position])
                        adapter.submitData(songs)
                    }
                })
            }
        })
        binding.btnPlay.setOnClickListener() {

        }
        favouriteViewModel.songs.observe(viewLifecycleOwner) {
            songs.clear()
            songs.addAll(it)
            Log.d(Contanst.TAG, it.toString())
            adapter.submitData(songs)
        }
    }
}