package com.example.musicplayer.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.musicplayer.R
import com.example.musicplayer.databinding.ActivityMusicPlayerBinding

class MusicPlayerActivity : AppCompatActivity() {
    companion object {
        private const val LOG = "TCR"
    }

    private lateinit var binding: ActivityMusicPlayerBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val toolBar = binding.toolbar
        setSupportActionBar(toolBar)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHost.navController

        // setupActionBarWithNavController(navController)
    }

}