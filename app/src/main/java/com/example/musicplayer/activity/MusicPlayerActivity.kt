package com.example.musicplayer.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.musicplayer.R
import com.example.musicplayer.databinding.ActivityMusicPlayerBinding
import com.example.musicplayer.vm.SongViewModel
import com.example.musicplayer.vm.SongViewModelFactory

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
        toolBar.overflowIcon?.setTint(Color.RED)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHost.navController

      //  setupActionBarWithNavController(navController)
    }

}