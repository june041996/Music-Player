package com.example.musicplayer.fragment.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapter.AddToPlaylistAdapter
import com.example.musicplayer.adapter.OnItemClickListener
import com.example.musicplayer.databinding.FragmentAddToPlaylistBinding
import com.example.musicplayer.model.Playlist
import com.example.musicplayer.utils.CustomDialog
import com.example.musicplayer.vm.PlaylistViewModel
import com.example.musicplayer.vm.SongViewModel


class AddToPlaylistFragment : Fragment() {
    private lateinit var binding: FragmentAddToPlaylistBinding
    private val playlistViewModel: PlaylistViewModel by activityViewModels()
    private val songViewModel: SongViewModel by activityViewModels()
    private var playlists = arrayListOf<Playlist>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddToPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCreatePlaylist.setOnClickListener() {
            CustomDialog(requireContext()).createConfirmDialog(object :
                CustomDialog.OnSubmitBtnClick {
                override fun onClick(name: String) {
                    playlistViewModel.insertPlaylist(name)
                }
            })
        }
        val adapter = AddToPlaylistAdapter()
        binding.rvPlaylist.adapter = adapter
        binding.rvPlaylist.layoutManager = LinearLayoutManager(context)
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                songViewModel.selectedSong.observe(viewLifecycleOwner) {
                    playlistViewModel.insertSongPlaylist(
                        it.idSong!!,
                        playlists[position].idPlaylist!!
                    )
                    Toast.makeText(context, "add success", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }

        })
        playlistViewModel.playlists.observe(viewLifecycleOwner) {
            playlists.clear()
            adapter.submitData(it)
            playlists.addAll(it)
        }
    }
}