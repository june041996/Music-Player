package com.example.musicplayer.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.activity.MusicPlayerActivity
import com.example.musicplayer.adapter.OnItemButtonClickListener
import com.example.musicplayer.adapter.OnItemClickListener
import com.example.musicplayer.adapter.rank.ItemViewOnClick
import com.example.musicplayer.adapter.rank.RankAdapter
import com.example.musicplayer.databinding.FragmentRankBinding
import com.example.musicplayer.model.Song
import com.example.musicplayer.utils.Contanst
import com.example.musicplayer.utils.Status
import com.example.musicplayer.vm.MusicPlayerViewModel
import com.example.musicplayer.vm.RankViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RankFragment : Fragment() {
    companion object {
        private const val LOG: String = "TCR"
        var listRankSong: ArrayList<Song> = arrayListOf()
    }

    private lateinit var binding: FragmentRankBinding
    private val viewModel: RankViewModel by activityViewModels()
    private val viewModelMusicPlayer: MusicPlayerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankBinding.inflate(inflater, container, false)
        val adapter = RankAdapter()
        binding.rvRank.adapter = adapter
        lifecycleScope.launch {
            viewModel.getSongsOnlineFromDB().observe(viewLifecycleOwner) {
                it?.let { it ->
                    when (it.status) {
                        Status.SUCCESS -> {
                            it.data?.observe(viewLifecycleOwner) { listSong ->
                                val list:List<Song> = listSong.sortedByDescending { it.views }
                                adapter.submitData(list)
                                listRankSong.addAll(listSong)

                            }
                        }
                        Status.LOADING -> {
                            Log.d(LOG, "Loading")
                        }
                        Status.ERROR -> {
                            Log.d(LOG, "Error")
                        }
                    }
                }
            }
        }
        binding.rvRank.layoutManager = LinearLayoutManager(context)
        val decoration: RecyclerView.ItemDecoration =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.rvRank.addItemDecoration(decoration)



        adapter.setItemViewOnClick(object : ItemViewOnClick {
            override fun onClick(song: Song, pos: Int) {
                song.idSong?.let { viewModelMusicPlayer.setIdSong(it) }
                viewModelMusicPlayer.idSong.observe(viewLifecycleOwner) { idSong ->

                    val intentSong = Intent(requireContext(), MusicPlayerActivity::class.java)

                    val bundle = Bundle()
                    bundle.putInt("pos", pos)
                    bundle.putInt("idSong", idSong)
                    bundle.putString("list", "listRankSong")
                    intentSong.putExtras(bundle)
                    startActivity(intentSong)

                }
            }
        })

//        adapter.setOnItemClickListener(
//            object : OnItemClickListener {
//                override fun onItemClick(position: Int) {
//                    Log.d(Contanst.TAG, position.toString())
//                }
//
//            }, object : OnItemButtonClickListener {
//                override fun onItemClick(position: Int, view: View) {
//                    //
//                }
//
//            }
//        )
        return binding.root
    }

    fun favourite() {

    }
}