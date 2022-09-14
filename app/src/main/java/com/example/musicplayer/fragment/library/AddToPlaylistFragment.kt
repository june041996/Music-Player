package com.example.musicplayer.fragment.library

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapter.library.AddedPlaylistAdapter
import com.example.musicplayer.adapter.library.NewPlaylistAdapter
import com.example.musicplayer.adapter.library.OnItemButtonClickListener
import com.example.musicplayer.adapter.library.OnItemClickListener
import com.example.musicplayer.databinding.FragmentAddToPlaylistBinding
import com.example.musicplayer.model.Playlist
import com.example.musicplayer.model.Song
import com.example.musicplayer.utils.Contanst
import com.example.musicplayer.utils.CustomDialog
import com.example.musicplayer.vm.PlaylistViewModel
import com.example.musicplayer.vm.SongViewModel


class AddToPlaylistFragment : Fragment() {
    private lateinit var binding: FragmentAddToPlaylistBinding
    private val playlistViewModel: PlaylistViewModel by activityViewModels()
    private val songViewModel: SongViewModel by activityViewModels()
    private var playlists = arrayListOf<Playlist>()
    private var song: Song? = null
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
        Log.d(Contanst.TAG, "add song")

        songViewModel.selectedSong.observe(viewLifecycleOwner) {
            Log.d(Contanst.TAG, "sz: ${it.toString()}")
        }
        //playlistViewModel.getPlaylistOfSong(1009)
        /*playlistViewModel.playlistWithoutSong.observe(viewLifecycleOwner){
            Log.d(Contanst.TAG,"add to: ${it.toString()}")
        }*/
        binding.btnCreatePlaylist.setOnClickListener() {
            CustomDialog(requireContext()).createInputDialog(object :
                CustomDialog.OnSubmitBtnClick {
                override fun onClick(name: String) {
                    playlistViewModel.insertPlaylist(name)
                    playlistViewModel.getPlaylistWithoutSong()
                }
            })
        }
        //in
        val inPlaylist = arrayListOf<Playlist>()
        val adapter = AddedPlaylistAdapter()
        binding.rvPlaylist.adapter = adapter
        binding.rvPlaylist.layoutManager = LinearLayoutManager(context)
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                songViewModel.selectedSong.observe(viewLifecycleOwner) {

                    // findNavController().popBackStack()
                }
            }
        }, object : OnItemButtonClickListener {
            override fun onItemClick(position: Int, view: View) {
                //remove
                Log.d(
                    Contanst.TAG,
                    "remove: ${song!!.idSong.toString()}- ${inPlaylist[position].idPlaylist.toString()}"
                )
                playlistViewModel.deleteSongInPlaylist(
                    inPlaylist[position]!!.idPlaylist!!,
                    song!!.idSong!!,

                    )
            }

        })
        playlistViewModel.playlistOfSong.observe(viewLifecycleOwner) {
            inPlaylist.clear()
            inPlaylist.addAll(it)
            adapter.submitData(inPlaylist)

        }
        //out
        val out = arrayListOf<Playlist>()
        val adapterOut = NewPlaylistAdapter().apply {
            setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    //open playlist
                }

            }, object : OnItemButtonClickListener {
                override fun onItemClick(position: Int, view: View) {
                    //add
                    Log.d(Contanst.TAG, "song: ${song!!.idSong!!}-p:${out[position].idPlaylist!!}")
                    playlistViewModel.insertSongInPlaylist(
                        song!!.idSong!!,
                        out[position].idPlaylist!!
                    )
                    Toast.makeText(context, "add success", Toast.LENGTH_SHORT).show()
                }

            })
        }
        binding.rvOutPlaylist.adapter = adapterOut
        binding.rvOutPlaylist.layoutManager = LinearLayoutManager(context)
        songViewModel.selectedSong.observe(viewLifecycleOwner) {
            song = it
            Log.d(Contanst.TAG, "song: ${song!!.nameSong.toString()}")
            playlistViewModel.getPlaylistOfSong(song!!.idSong!!)
            /*playlistViewModel.playlistOfSong.observe(viewLifecycleOwner){
                Log.d(Contanst.TAG,"p: ${it.toString()}")*/
            playlistViewModel.playlistWithoutSong.observe(viewLifecycleOwner) {
                Log.d(Contanst.TAG, "it: ${it.toString()}")
                out.clear()
                out.addAll(it)
                adapterOut.submitData(out)
            }
            //}
        }


    }
}