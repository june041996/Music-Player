package com.example.musicplayer.fragment


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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.activity.MainActivity
import com.example.musicplayer.activity.MusicPlayerActivity
import com.example.musicplayer.adapter.ItemOnclick
import com.example.musicplayer.adapter.library.OnItemButtonClickListener
import com.example.musicplayer.adapter.library.OnItemClickListener
import com.example.musicplayer.adapter.ListSongFavoriteAdapter
import com.example.musicplayer.adapter.ListSongPlaylistAdapter
import com.example.musicplayer.databinding.FragmentHomeBinding
import com.example.musicplayer.fragment.library.PlaylistFragment
import com.example.musicplayer.fragment.library.PlaylistFragmentDirections
import com.example.musicplayer.model.Playlist
import com.example.musicplayer.model.Song
import com.example.musicplayer.utils.Contanst
import com.example.musicplayer.utils.Contanst.TAG
import com.example.musicplayer.utils.CustomDialog
import com.example.musicplayer.vm.FavouriteViewModel
import com.example.musicplayer.vm.MusicPlayerViewModel
import com.example.musicplayer.vm.PlaylistViewModel
import com.example.musicplayer.vm.SongViewModel
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
    private val playlistViewModel: PlaylistViewModel by activityViewModels()


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

            Log.d(LOG, "call data id: ${mSongViewModel.id}")
            adapter.submitData(it)
        }
        binding.recycleViewPlaylist.adapter = adapter

        binding.recycleViewPlaylist.layoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
        //onclick playlist

//        adapter.setOnItemClickListener(object :ItemOnclick{
//            override fun onClick(song: Song) {
//                Log.d(TAG, position.toString())
//                playlistViewModel.setSelectedPlaylist(PlaylistFragment.playlists[position])
//                findNavController().navigate(
//                    PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistSongFragment(
//                        "Playlist: ${PlaylistFragment.playlists[position].name}"
//                    )
//                )
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

    private fun showMenuPopup(v: View, playlist: Playlist) {
        PopupMenu(requireContext(), v).apply {
            menuInflater.inflate(R.menu.item_playlist_menu, menu)
            show()
            setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.edit -> {
                            CustomDialog(requireContext()).createEditDialog(playlist.name,
                                object : CustomDialog.OnSubmitBtnClick {
                                    override fun onClick(name: String) {
                                        playlistViewModel.updatePlaylist(
                                            name,
                                            playlist.idPlaylist!!
                                        )
                                    }

                                })
                        }
                        R.id.delete -> {
                            CustomDialog(requireContext()).createConfirmDialog(object :
                                CustomDialog.OnSubmitBtnClick {
                                override fun onClick(name: String) {
                                    playlistViewModel.deletePlaylist(playlist.idPlaylist!!)
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


