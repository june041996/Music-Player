package com.example.musicplayer.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayer.activity.MusicPlayerActivity
import com.example.musicplayer.databinding.FragmentHomeBinding
import com.example.musicplayer.model.User
import com.example.musicplayer.vm.AuthViewModel
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var viewModel: AuthViewModel

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


        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        return binding.root
    }

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

}