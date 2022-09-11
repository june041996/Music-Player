package com.example.musicplayer.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapter.ListSongPlaylistAdapter
import com.example.musicplayer.databinding.FragmentHomeBinding
import com.example.musicplayer.model.Song
import com.example.musicplayer.vm.SongViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val mSongViewModel: SongViewModel by activityViewModels()
    var song = listOf<Song>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        //recycleview
        val adapter = ListSongPlaylistAdapter()

        //get all Song
        mSongViewModel.localSongs.observe(viewLifecycleOwner) {
            Log.d("QuangLPT", "call data: ${it.size}")
            adapter.submitData(it)
        }
        binding.recycleViewPlaylist.adapter = adapter
        binding.recycleViewPlaylist.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        return binding.root
    }


}