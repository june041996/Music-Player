package com.example.musicplayer.fragment.library

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.adapter.FavouriteSongAdapter
import com.example.musicplayer.adapter.OnItemButtonClickListener
import com.example.musicplayer.adapter.OnItemClickListener
import com.example.musicplayer.databinding.FragmentFavouriteBinding
import com.example.musicplayer.model.Song
import com.example.musicplayer.utils.Contanst
import com.example.musicplayer.vm.FavouriteViewModel


class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private var songs = arrayListOf<Song>()
    private val favouriteViewModel: FavouriteViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FavouriteSongAdapter()
        binding.rvSongs.adapter = adapter
        binding.rvSongs.layoutManager = LinearLayoutManager(context)
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d(Contanst.TAG, "item: ${songs[position].nameSong}")

            }

        }, object : OnItemButtonClickListener {
            override fun onItemClick(position: Int, view: View) {
                //remove favourite
                createDialog(object : OnSubmitBtnClick {
                    override fun onClick() {
                        favouriteViewModel.removeFavouriteSong(songs[position])
                        songs.remove(songs[position])
                        adapter.submitData(songs)
                    }

                })

            }

        })
        /*val songs = arrayListOf<Song>(
            Song(1,"1","","","","","","",0,"",0,true),
            Song(1,"2","","","","","","",0,"",0,true),
            Song(1,"3","","","","","","",0,"",0,true),
        )*/
        binding.imgPlay.setOnClickListener() {

        }
        favouriteViewModel.songs.observe(viewLifecycleOwner) {
            songs.clear()
            songs.addAll(it)
            Log.d(Contanst.TAG, it.toString())
            adapter.submitData(songs)
        }
    }

    private fun createDialog(onSubmitBtnClick: OnSubmitBtnClick) {
        val dialog = Dialog(requireContext()).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_confirm_delete_favourite)
            window?.apply {
                setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                attributes.gravity = Gravity.CENTER
            }
            setCancelable(true)
        }
        dialog.show()
        dialog.findViewById<Button>(R.id.btnCancel).setOnClickListener() {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btnOk).setOnClickListener() {
            onSubmitBtnClick.onClick()
            dialog.dismiss()
        }
    }

    interface OnSubmitBtnClick {
        fun onClick()
    }
}