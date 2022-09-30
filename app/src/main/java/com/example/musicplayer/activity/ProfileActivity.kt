package com.example.musicplayer.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.adapter.profile.ProfileAdapter
import com.example.musicplayer.databinding.ActivityProfileBinding
import com.example.musicplayer.vm.UserViewMode
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val mUserModel: UserViewMode by viewModels()
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    @Inject
    lateinit var sharedpreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = sharedpreferences.getInt("id", 0)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            mAuth.currentUser?.let {
            }
        }
        val adapter = ProfileAdapter()
        lifecycleScope.launch {
            mUserModel.getUserById(id).observe(this@ProfileActivity) {
                val l = arrayListOf<com.example.musicplayer.model.User>()
                l.add(it)
                adapter.submitData(l)

            }

        }

        mUserModel.user.observe(this) {

            adapter.submitData(it)

        }
        binding.recyclerView.adapter = adapter

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        val toolBar = binding.toolbar
        setSupportActionBar(toolBar)
        binding.logout.setOnClickListener {
            // mAuth.signOut()
            val editor = sharedpreferences.edit()
            editor.clear()
            editor.apply()

            startActivity(Intent(this, SigninActivity::class.java))
        }

    }


}