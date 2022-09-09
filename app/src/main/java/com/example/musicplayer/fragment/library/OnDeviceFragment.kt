package com.example.musicplayer.fragment.library

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapter.OnDeviceAdapter
import com.example.musicplayer.adapter.OnItemClickListener
import com.example.musicplayer.databinding.FragmentOnDeviceBinding
import com.example.musicplayer.model.Song
import com.example.musicplayer.utils.Contanst
import com.example.musicplayer.vm.SongViewModel


class OnDeviceFragment : Fragment() {
    private lateinit var binding: FragmentOnDeviceBinding
    private val songViewModel: SongViewModel by activityViewModels()
    private var localSongs = arrayListOf<Song>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnDeviceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = OnDeviceAdapter()
        binding.rvSongs.adapter = adapter
        binding.rvSongs.layoutManager = LinearLayoutManager(context)
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d(Contanst.TAG, "item: ${localSongs[position].nameSong}")
            }
        })
        songViewModel.localSongs.observe(viewLifecycleOwner) {
            localSongs.clear()
            localSongs.addAll(it)
            adapter.submitData(localSongs)
        }

        binding.imgPlay.setOnClickListener() {

        }
    }
}