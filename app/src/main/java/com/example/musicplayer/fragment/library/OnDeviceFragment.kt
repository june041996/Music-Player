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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.adapter.OnDeviceAdapter
import com.example.musicplayer.adapter.OnItemButtonClickListener
import com.example.musicplayer.adapter.OnItemClickListener
import com.example.musicplayer.databinding.FragmentOnDeviceBinding
import com.example.musicplayer.model.Song
import com.example.musicplayer.utils.Contanst
import com.example.musicplayer.vm.FavouriteViewModel
import com.example.musicplayer.vm.PlaylistViewModel
import com.example.musicplayer.vm.SongViewModel


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
        songViewModel.sizeLocalSongs.observe(viewLifecycleOwner) {
            binding.tvSizeSongs.text = "${it.toString()} Track"
        }
        val adapter = OnDeviceAdapter()
        binding.rvSongs.adapter = adapter
        binding.rvSongs.layoutManager = LinearLayoutManager(context)
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                //play music
                Log.d(Contanst.TAG, "item: ${localSongs[position].nameSong}")

            }
        }, object : OnItemButtonClickListener {
            override fun onItemClick(position: Int, view: View) {
                //show menu
                songViewModel.setSelectSong(localSongs[position])
                showMenuPopup(view, localSongs[position])
            }

        })
        songViewModel.localSongs.observe(viewLifecycleOwner) {
            localSongs.clear()
            localSongs.addAll(it)
            adapter.submitData(localSongs)
        }

        binding.btnPlay.setOnClickListener() {
            //play all songs
        }
    }

    private fun showMenuPopup(v: View, song: Song) {
        val popupMenu = PopupMenu(requireContext(), v)
        popupMenu.menuInflater.inflate(R.menu.song_menu, popupMenu.menu)
        popupMenu.show()
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.favourite -> {
                        favouriteViewModel.insertFavourite(song)
                    }
                    R.id.addToPlaylist -> {
                        findNavController().navigate(OnDeviceFragmentDirections.actionOnDeviceFragmentToAddToPlaylistFragment())
                    }
                }
                return true
            }
        })
    }
}