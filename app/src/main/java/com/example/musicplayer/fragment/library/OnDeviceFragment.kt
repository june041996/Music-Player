package com.example.musicplayer.fragment.library

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.adapter.OnDeviceAdapter
import com.example.musicplayer.adapter.OnItemClickListener
import com.example.musicplayer.databinding.FragmentOnDeviceBinding
import com.example.musicplayer.db.MusicDatabase
import com.example.musicplayer.model.Song
import com.example.musicplayer.utils.Contanst
import com.example.musicplayer.vm.FavouriteViewModel
import com.example.musicplayer.vm.PlaylistViewModel
import com.example.musicplayer.vm.SongViewModel
import kotlinx.coroutines.launch


class OnDeviceFragment : Fragment() {
    private lateinit var binding: FragmentOnDeviceBinding
    private val songViewModel: SongViewModel by activityViewModels()
    private val playlistViewModel: PlaylistViewModel by activityViewModels()
    private val favouriteViewModel: FavouriteViewModel by activityViewModels()
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
            override fun onItemClick(position: Int,view: View) {
                Log.d(Contanst.TAG, "item: ${localSongs[position].nameSong}")
                showMenuPopup(view,localSongs[position])
            }
        })
        songViewModel.localSongs.observe(viewLifecycleOwner) {
            localSongs.clear()
            localSongs.addAll(it)
            adapter.submitData(localSongs)
        }

        binding.imgPlay.setOnClickListener() {
            //favouriteViewModel.getAllSongs()
            lifecycleScope.launch {
                val dao = MusicDatabase.getInstance(requireContext()).songDao()
                val a = dao.getSongsOfFavourite(1)
                Log.d(Contanst.TAG,a.toString())
            }

        }
    }
    private fun showMenuPopup(v: View,song: Song){
       val popupMenu = PopupMenu(requireContext(),v)
        popupMenu.menuInflater.inflate(R.menu.song_menu,popupMenu.menu)
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when(item.itemId){
                    R.id.favourite -> {
                        Log.d(Contanst.TAG,"favou")
                        favouriteViewModel.insertFavourite(song)
                    }
                    R.id.addToPlaylist -> {
                        Log.d(Contanst.TAG, "one")

                    }
                }
               return true
            }

        })
    }
}