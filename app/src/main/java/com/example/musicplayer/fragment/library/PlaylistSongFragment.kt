package com.example.musicplayer.fragment.library

import android.content.Intent
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
import com.example.musicplayer.activity.MusicPlayerActivity
import com.example.musicplayer.adapter.library.OnItemButtonClickListener
import com.example.musicplayer.adapter.library.OnItemClickListener
import com.example.musicplayer.adapter.library.SongsOfPlaylistAdapter
import com.example.musicplayer.adapter.library.SuggestSongsAdapter
import com.example.musicplayer.databinding.FragmentPlaylistSongBinding
import com.example.musicplayer.model.Song
import com.example.musicplayer.utils.Contanst
import com.example.musicplayer.utils.CustomDialog
import com.example.musicplayer.vm.FavouriteViewModel
import com.example.musicplayer.vm.PlaylistViewModel


class PlaylistSongFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistSongBinding
    private val playlistViewModel: PlaylistViewModel by activityViewModels()
    private val favouriteViewModel: FavouriteViewModel by activityViewModels()

    companion object {
        var songs = arrayListOf<Song>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlaylistSongBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.playlist = playlistViewModel
        binding.lifecycleOwner = this
        //playlistViewModel.getSuggestSongs()

        //songs of playlist
        val adapter = SongsOfPlaylistAdapter()
        binding.rvSongs.adapter = adapter
        binding.rvSongs.layoutManager = LinearLayoutManager(context)
        playlistViewModel.songsOfPlaylist.observe(viewLifecycleOwner) {
            songs.clear()
            songs.addAll(it)
            Log.d(Contanst.TAG, "or: ${it.size.toString()}")

            adapter.submitData(songs)
        }

        binding.btnPlay.setOnClickListener {
            val song = songs[0]
            val intentSong = Intent(requireContext(), MusicPlayerActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("pos", 0)
            bundle.putSerializable("song", song)
            bundle.putString("list", "listPlaylist")
            intentSong.putExtras(bundle)
            startActivity(intentSong)
        }


        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val song = songs[position]
                val intentSong = Intent(requireContext(), MusicPlayerActivity::class.java)
                val bundle = Bundle()
                bundle.putInt("pos", position)
                bundle.putSerializable("song", song)
                bundle.putString("list", "listPlaylist")
                intentSong.putExtras(bundle)
                startActivity(intentSong)
            }

        }, object : OnItemButtonClickListener {
            override fun onItemClick(position: Int, view: View) {
                showMenuPopup(view, songs[position])
            }

        })


        //suggest songs
        val suggestSongs = arrayListOf<Song>()
        val adapterSuggest = SuggestSongsAdapter()
        binding.rvSuggestSongs.adapter = adapterSuggest
        binding.rvSuggestSongs.layoutManager = LinearLayoutManager(context)

        adapterSuggest.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d(Contanst.TAG, position.toString())
                favouriteViewModel.checkFavouriteSong(suggestSongs[position].idSong!!)
                favouriteViewModel.checkSong.observe(viewLifecycleOwner) {
                    Log.d(Contanst.TAG, "check: ${it.toString()}")
                }
            }

        }, object : OnItemButtonClickListener {
            override fun onItemClick(position: Int, view: View) {
                //add
                Log.d(Contanst.TAG, position.toString())
                playlistViewModel.selectedPlaylist.observe(viewLifecycleOwner) {
                    playlistViewModel.insertSongPlaylist(
                        suggestSongs[position].idSong!!,
                        it.idPlaylist!!
                    )
                }

            }

        })
        playlistViewModel.suggestSongs.observe(viewLifecycleOwner) {
            Log.d(Contanst.TAG, "su: ${it.size.toString()}")
            suggestSongs.clear()
            suggestSongs.addAll(it)
            adapterSuggest.submitData(suggestSongs)
        }
    }

    private fun showMenuPopup(v: View, song: Song) {

        PopupMenu(requireContext(), v).apply {
            menuInflater.inflate(R.menu.song_of_playlist_menu, menu)
            show()
            setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.favourite -> {
                            Log.d(Contanst.TAG, "favou")
                            favouriteViewModel.insertFavourite(song)
                        }
                        R.id.addToPlaylist -> {
                            //Log.d(Contanst.TAG, "pla")
                            findNavController().navigate(
                                PlaylistSongFragmentDirections.actionPlaylistSongFragmentToAddToPlaylistFragment(
                                    song.nameSong!!
                                )
                            )
                        }
                        R.id.delete -> {
                            CustomDialog(requireContext()).createConfirmDialog(object :
                                CustomDialog.OnSubmitBtnClick {
                                override fun onClick(name: String) {
                                    playlistViewModel.deleteSongOfPlaylist(song)
                                }
                            })
                        }
                    }
                    return true
                }

            })
        }
    }
}