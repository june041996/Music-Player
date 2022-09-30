package com.example.musicplayer.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.adapter.library.OnDeviceAdapter
import com.example.musicplayer.adapter.library.OnItemButtonClickListener
import com.example.musicplayer.adapter.library.OnItemClickListener
import com.example.musicplayer.databinding.FragmentSearchBinding
import com.example.musicplayer.extension.onQueryTextChanged
import com.example.musicplayer.model.Song
import com.example.musicplayer.utils.Contanst
import com.example.musicplayer.vm.FavouriteViewModel
import com.example.musicplayer.vm.SearchViewModel
import com.example.musicplayer.vm.SongViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val songViewModel: SongViewModel by activityViewModels()
    private val favouriteViewModel: FavouriteViewModel by activityViewModels()
    private val searchViewModel: SearchViewModel by activityViewModels()

    companion object {
        var songs = arrayListOf<Song>()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = OnDeviceAdapter()
        binding.rvSongs.adapter = adapter
        binding.rvSongs.layoutManager = LinearLayoutManager(context)
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                //play music
                Log.d(Contanst.TAG, "item: ${songs[position].nameSong}")
                /*val intent = Intent(context, MusicPlayerActivity::class.java)
                intent.putExtra("song", localSongs[position])
                startActivity(intent)*/
            }
        }, object : OnItemButtonClickListener {
            override fun onItemClick(position: Int, view: View) {
                //show menu
                songViewModel.setSelectSong(songs[position])
                showMenuPopup(view, songs[position])
            }

        })
        songViewModel.songs.observe(viewLifecycleOwner) {
            songs.clear()
            songs.addAll(it)
            adapter.submitData(songs)
        }
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menu.clear()
                menuInflater.inflate(R.menu.search_menu, menu)
                val searchItem = menu.findItem(R.id.search)
                val searchView: androidx.appcompat.widget.SearchView? =
                    searchItem.actionView as androidx.appcompat.widget.SearchView?
                searchView?.onQueryTextChanged {
                    searchViewModel.searchQuery.value = it
                    searchViewModel.allSongs.observe(parentFragment!!.viewLifecycleOwner) {
                        songs.clear()
                        songs.addAll(it)
                        adapter.submitData(songs)
                    }
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
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
                            SearchFragmentDirections.actionSearchFragmentToAddToPlaylistFragment(
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