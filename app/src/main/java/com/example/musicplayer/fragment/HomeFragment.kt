package com.example.musicplayer.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.musicplayer.activity.MusicPlayerActivity
import com.example.musicplayer.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.btnMusicPlayer.setOnClickListener {
            startActivity(Intent(requireActivity(), MusicPlayerActivity::class.java))
//            val action = HomeFragmentDirections.actionHomeFragmentToMusicPlayerFragment()
//            findNavController().navigate(action)
        }
        return binding.root
    }
}