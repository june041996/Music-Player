package com.example.musicplayer.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapter.ListSongFavoriteAdapter
import com.example.musicplayer.adapter.ListSongPlaylistAdapter
import com.example.musicplayer.databinding.FragmentHomeBinding
import com.example.musicplayer.model.Song
import com.example.musicplayer.vm.FavouriteViewModel
import com.example.musicplayer.vm.PlaylistViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val mSongViewModel: PlaylistViewModel by activityViewModels()
    private val mFavoretiViewModel: FavouriteViewModel by activityViewModels()
    var song = listOf<Song>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val adapter = ListSongPlaylistAdapter()

        //get all Song
        mSongViewModel.playlists.observe(viewLifecycleOwner) {
            Log.d("QuangLPT", "call data song: ${it.size}")
            adapter.submitData(it)
        }
        binding.recycleViewPlaylist.adapter = adapter

        binding.recycleViewPlaylist.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)

        //recycleview favorite
        val adapterFavorite = ListSongFavoriteAdapter()
        //get all Favorite
        mFavoretiViewModel.songs.observe(viewLifecycleOwner) {
            Log.d("QuangLPT", "call data favorite: ${it.size}")
            adapterFavorite.submitDataFavorite(it)
        }

        binding.recycleViewFavorite.adapter = adapterFavorite
        binding.recycleViewFavorite.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return binding.root
    }

}