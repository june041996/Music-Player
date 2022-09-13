package com.example.musicplayer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.musicplayer.databinding.FragmentLibraryBinding


class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentLibraryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lnOnDevice.setOnClickListener() {
            findNavController().navigate(LibraryFragmentDirections.actionLibraryFragmentToOnDeviceFragment())
        }
        binding.lnFavourite.setOnClickListener() {
            findNavController().navigate(LibraryFragmentDirections.actionLibraryFragmentToFavouriteFragment())
        }
        binding.lnPlaylist.setOnClickListener() {
            findNavController().navigate(LibraryFragmentDirections.actionLibraryFragmentToPlaylistFragment())
        }
    }
}