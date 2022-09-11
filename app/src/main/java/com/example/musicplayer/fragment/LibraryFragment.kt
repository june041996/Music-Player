package com.example.musicplayer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.musicplayer.databinding.FragmentLibraryBinding
import com.example.musicplayer.model.User
import com.example.musicplayer.vm.UserViewMode


class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentLibraryBinding
    private val userViewModel: UserViewMode by activityViewModels()
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
        userViewModel.insertUser(User(1, "a", ""))
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