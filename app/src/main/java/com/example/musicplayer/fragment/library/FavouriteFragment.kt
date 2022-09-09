package com.example.musicplayer.fragment.library

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.adapter.OnDeviceAdapter
import com.example.musicplayer.adapter.OnItemClickListener
import com.example.musicplayer.databinding.FragmentFavouriteBinding
import com.example.musicplayer.model.Song
import com.example.musicplayer.utils.Contanst
import com.example.musicplayer.vm.FavouriteViewModel


class FavouriteFragment : Fragment() {
    private lateinit var binding : FragmentFavouriteBinding
    private var songs = arrayListOf<Song>()
    private val favouriteViewModel: FavouriteViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = OnDeviceAdapter()
        binding.rvSongs.adapter = adapter
        binding.rvSongs.layoutManager = LinearLayoutManager(context)
        adapter.setOnItemClickListener(object : OnItemClickListener{
            override fun onItemClick(position: Int, view: View) {
                TODO("Not yet implemented")
            }

        })
        /*val songs = arrayListOf<Song>(
            Song(1,"1","","","","","","",0,"",0,true),
            Song(1,"2","","","","","","",0,"",0,true),
            Song(1,"3","","","","","","",0,"",0,true),
        )*/
        binding.imgPlay.setOnClickListener(){

        }
        favouriteViewModel.songs.observe(viewLifecycleOwner){
            songs.clear()
            songs.addAll(it)
            Log.d(Contanst.TAG,it.toString())
            adapter.submitData(songs)
        }
    }
}