package com.example.musicplayer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.musicplayer.databinding.ActivitySigninBinding
import com.example.musicplayer.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}