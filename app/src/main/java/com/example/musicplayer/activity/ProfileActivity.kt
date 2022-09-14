package com.example.musicplayer.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapter.profile.ProfileAdapter

import com.example.musicplayer.databinding.ActivityProfileBinding
import com.example.musicplayer.vm.UserViewMode
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val mUserModel: UserViewMode by viewModels()
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    @Inject
    lateinit var sharedpreferences: SharedPreferences
    private val SHARED_PREFS = "shared_prefs"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            mAuth.currentUser?.let {
            }
        }
        val adapter = ProfileAdapter()
        mUserModel.user.observe(this){
            adapter.submitData(it)

        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        val toolBar = binding.toolbar
        setSupportActionBar(toolBar)
        binding.logout.setOnClickListener {
            mAuth.signOut()
            sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            val editor = sharedpreferences.edit()
            editor.clear()
            editor.apply()

            startActivity(Intent(this, SigninActivity::class.java))
        }

    }


}