package com.example.musicplayer.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
<<<<<<< HEAD

=======
<<<<<<< HEAD
>>>>>>> 167b27c3d563c22aa5c364c3323831283280ab8e
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.Adapter.ListSongPlaylistAdapter
import com.example.musicplayer.R
<<<<<<< HEAD

=======
=======
import com.example.musicplayer.activity.MusicPlayerActivity
>>>>>>> origin/master
>>>>>>> 167b27c3d563c22aa5c364c3323831283280ab8e
import com.example.musicplayer.databinding.FragmentHomeBinding
import com.example.musicplayer.model.Song
import com.example.musicplayer.model.User
import com.example.musicplayer.vm.AuthViewModel
import com.example.musicplayer.vm.SongViewModel
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val mSongViewModel: SongViewModel by activityViewModels()
    var song = listOf<Song>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

<<<<<<< HEAD


        //recycleview
        val adapter = ListSongPlaylistAdapter()

        //get all Song
        mSongViewModel.localSongs.observe(viewLifecycleOwner){
            Log.d("QuangLPT","call data: ${it.size}")
           adapter.submitData(it)
        }
        binding.recycleViewPlaylist.adapter = adapter
        binding.recycleViewPlaylist.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
=======
<<<<<<< HEAD

        //recycleview
        val adapter = ListSongPlaylistAdapter()

        //get all Song
        mSongViewModel.localSongs.observe(viewLifecycleOwner){
            Log.d("QuangLPT","call data: ${it.size}")
           adapter.submitData(it)
        }
        binding.recycleViewPlaylist.adapter = adapter
        binding.recycleViewPlaylist.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        return binding.root
    }

=======
        binding.btnMusicPlayer.setOnClickListener {
            startActivity(Intent(requireActivity(), MusicPlayerActivity::class.java))
//            val action = HomeFragmentDirections.actionHomeFragmentToMusicPlayerFragment()
//            findNavController().navigate(action)
        }


        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
>>>>>>> 167b27c3d563c22aa5c364c3323831283280ab8e

        return binding.root
    }

<<<<<<< HEAD

=======
    private fun checkUser() {
        //check user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            //user not null, user is logged in, get user info
            val email = firebaseUser.email
            //set to text view
            viewModel.insertUser(User(idUser = null, email = email!!, password = ""))
            binding.tvEmail.text = email


        }

    }
>>>>>>> origin/master
>>>>>>> 167b27c3d563c22aa5c364c3323831283280ab8e

}