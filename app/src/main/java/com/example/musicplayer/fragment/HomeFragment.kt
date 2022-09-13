package com.example.musicplayer.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager


import com.example.musicplayer.activity.MusicPlayerActivity
import com.example.musicplayer.activity.SigninActivity
import com.example.musicplayer.adapter.ItemOnclick
import com.example.musicplayer.adapter.ListRankAdapter

import com.example.musicplayer.adapter.ListSongFavoriteAdapter
import com.example.musicplayer.adapter.ListSongPlaylistAdapter
import com.example.musicplayer.adapter.rank.ItemViewOnClick
import com.example.musicplayer.databinding.FragmentHomeBinding
import com.example.musicplayer.model.Playlist
import com.example.musicplayer.model.Song

import com.example.musicplayer.vm.*
import com.google.firebase.auth.FirebaseAuth



class HomeFragment : Fragment() {
    companion object {
        private const val LOG: String = "QuangLPT"
    }

    private lateinit var binding: FragmentHomeBinding

    private val mSongViewModel: PlaylistViewModel by activityViewModels()
    private val mFavoretiViewModel: FavouriteViewModel by activityViewModels()
    private val mRankViewModel: SongViewModel by activityViewModels()
    private val viewModelMusicPlayer: MusicPlayerViewModel by activityViewModels()
    var song = listOf<Song>()
    private val playlists = arrayListOf<Playlist>()
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        //recycleview Playlist

        val adapter = ListSongPlaylistAdapter()

        //get all Song
        mSongViewModel.playlists.observe(viewLifecycleOwner) {
            Log.d(LOG, "call data song: ${it.size}")
           // Log.d(LOG, "call data id: ${mSongViewModel.id}")
           adapter.submitData(it)
        }
        binding.recycleViewPlaylist.adapter = adapter

        binding.recycleViewPlaylist.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
        //onclick playlist
//        adapter.setOnItemClickListener(object :OnItemClickListener{
//            override fun onItemClick(position: Int) {
//                mSongViewModel.setSelectedPlaylist(playlists[position])
//                val intent = Intent(requireContext(),PlaylistSongFragment::class.java)
//
//              startActivity(intent)
//            }
//        })


        //recycleview favorite
        val adapterFavorite = ListSongFavoriteAdapter()
        //get all Favorite
        mFavoretiViewModel.songs.observe(viewLifecycleOwner) {
            Log.d(LOG, "call data favorite: ${it.size}")
            adapterFavorite.submitDataFavorite(it)
        }

        binding.recycleViewFavorite.adapter = adapterFavorite
        binding.recycleViewFavorite.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        //onclick favorite
        adapterFavorite.setOnItemClickListener(object : ItemOnclick {
            override fun onClick(song: Song) {
                song.idSong?.let {
                    viewModelMusicPlayer.setIdSong(it)
                }
                viewModelMusicPlayer.idSong.observe(viewLifecycleOwner) { idSong ->
                    val intent = Intent(requireContext(), MusicPlayerActivity::class.java)
                    intent.putExtra("idSong", idSong)
                    startActivity(intent)
                }

            }

        })
        
        return binding.root
    }


}


