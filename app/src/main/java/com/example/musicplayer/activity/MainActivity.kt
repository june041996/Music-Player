package com.example.musicplayer.activity


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.musicplayer.R
import com.example.musicplayer.databinding.ActivityMainBinding
import com.example.musicplayer.model.Song
import com.example.musicplayer.utils.Status
import com.example.musicplayer.vm.SongViewModel
import com.example.musicplayer.vm.SongViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration


    //   // private val viewModel: HomeViewModel by viewModels()
    private val viewModel: SongViewModel by viewModels {
        SongViewModelFactory(application)
    }

    companion object {
        private const val LOG = "TCR"
        private const val TAG: String = "DHP"
        private const val READ_STORAGE_PERMISSION_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val toolBar = binding.toolbar
        setSupportActionBar(toolBar)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHost.navController

        //Đồng cấp
        appBarConfig = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.searchFragment,
                R.id.rankFragment,
                R.id.libraryFragment,
            )
        )

        setupActionBarWithNavController(navController, appBarConfig)

        binding.apply {
            bnvMain.setupWithNavController(navController)
            navView.setupWithNavController(navController)
        }

        viewModel.localSongs.observe(this) {
            Log.d(TAG, "local songs: ${it.toString()}")
        }

        // check permission
        checkPermission(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            READ_STORAGE_PERMISSION_CODE
        )

        //Rin
        /////////////////////////////////////////////////////////////////////
        //INSERT to DB
        lifecycleScope.launch {
            viewModel.getSong().observe(this@MainActivity) {
                it?.let {
                    when (it.status) {
                        Status.SUCCESS -> {
                            val songs = it.data
                            Log.d(LOG, songs.toString())
                            for (i in 0..songs!!.size) {
                                val song = Song(
                                    songs[i].idSong,
                                    songs[i].nameSong,
                                    songs[i].urlImage,
                                    songs[i].urlSong,
                                    songs[i].genre,
                                    songs[i].musician,
                                    songs[i].singer,
                                    songs[i].album,
                                    songs[i].release,
                                    songs[i].duration,
                                    songs[i].views,
                                    songs[i].isOffline
                                )
                                viewModel.insertSongToDB(song)
                            }
                        }
                        Status.LOADING -> {
                            Log.d(LOG, "Loading")
                        }
                        Status.ERROR -> {
                            Log.d(LOG, "Error")
                        }
                    }
                }
            }
        }
        /////////////////////////////////////////////////////////////

  }


    //update local song between room and device
    private fun updateLocalSongs() {
        lifecycleScope.launch {
            delay(500L)
            viewModel.updateLocalSongs()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    //3
    // Function to check and request permission.
    private fun checkPermission(permission: Array<String>, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                permission[0]
            ) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(
                this@MainActivity,
                permission[1]
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this@MainActivity, permission, requestCode)
        } else {
            Toast.makeText(this@MainActivity, "Permission already granted", Toast.LENGTH_SHORT)
                .show()
            updateLocalSongs()
        }
    }

    //4
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this@MainActivity,
                    "READ and WRITE STORAGE Permission Granted",
                    Toast.LENGTH_SHORT
                )
                    .show()
                updateLocalSongs()
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "READ and WRITE STORAGE  Permission Denied",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

}