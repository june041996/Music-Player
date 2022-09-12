package com.example.musicplayer.fragment.library

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.activity.MusicPlayerActivity
import com.example.musicplayer.adapter.library.OnDeviceAdapter
import com.example.musicplayer.adapter.library.OnItemButtonClickListener
import com.example.musicplayer.adapter.library.OnItemClickListener
import com.example.musicplayer.databinding.FragmentOnDeviceBinding
import com.example.musicplayer.model.Song
import com.example.musicplayer.utils.Contanst
import com.example.musicplayer.vm.FavouriteViewModel
import com.example.musicplayer.vm.PlaylistViewModel
import com.example.musicplayer.vm.SongViewModel
import kotlinx.coroutines.delay
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
        binding.song = songViewModel
        binding.lifecycleOwner = this

        songViewModel.sizeLocalSongs.observe(viewLifecycleOwner) {
            Log.d(Contanst.TAG, "size: ${it.toString()}")
        }
        val adapter = OnDeviceAdapter()
        binding.rvSongs.adapter = adapter
        binding.rvSongs.layoutManager = LinearLayoutManager(context)
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                //play music
                Log.d(Contanst.TAG, "item: ${localSongs[position].nameSong}")
                val intent = Intent(context, MusicPlayerActivity::class.java)
                intent.putExtra("song", localSongs[position])
                startActivity(intent)
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
        binding.btnShuffle.setOnClickListener() {
            //songViewModel.updateLocalSongs()
        }
        binding.swipeFreshLayout.setOnRefreshListener() {
            songViewModel.updateLocalSongs()
            binding.swipeFreshLayout.isRefreshing = false
            adapter.submitData(localSongs)
        }
    }

    private fun showMenuPopup(v: View, song: Song) {
        PopupMenu(requireContext(), v).apply {
            menuInflater.inflate(R.menu.song_menu, menu)
            show()
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.favourite -> {
                        lifecycleScope.launch {
                            favouriteViewModel.checkFavouriteSong(song.idSong!!)
                            delay(500L)
                            if (favouriteViewModel.check) {
                                Toast.makeText(
                                    context,
                                    "This song already was added to favorites list",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                favouriteViewModel.insertFavourite(song)
                            }
                        }
                    }
                    R.id.addToPlaylist -> {
                        findNavController().navigate(
                            OnDeviceFragmentDirections.actionOnDeviceFragmentToAddToPlaylistFragment(
                                song.nameSong!!
                            )
                        )
                    }
                }
                true
            }
        }

    }
}