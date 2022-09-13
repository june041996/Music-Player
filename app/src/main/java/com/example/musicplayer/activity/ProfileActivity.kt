package com.example.musicplayer.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.musicplayer.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
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