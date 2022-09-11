package com.example.musicplayer.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.Adapter.ListSongPlaylistAdapter
import com.example.musicplayer.R

import com.example.musicplayer.databinding.FragmentHomeBinding
import com.example.musicplayer.model.Song
import com.example.musicplayer.model.User
import com.example.musicplayer.vm.AuthViewModel
import com.example.musicplayer.vm.SongViewModel
import com.google.firebase.auth.FirebaseAuth


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
        mSongViewModel.localSongs.observe(viewLifecycleOwner){
            Log.d("QuangLPT","call data: ${it.size}")
           adapter.submitData(it)
        }
        binding.recycleViewPlaylist.adapter = adapter
        binding.recycleViewPlaylist.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        return binding.root
    }



}