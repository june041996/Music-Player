package com.example.musicplayer.activity


import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.musicplayer.R
import com.example.musicplayer.databinding.ActivityMainBinding
import com.example.musicplayer.model.Song
import com.example.musicplayer.utils.Status
import com.example.musicplayer.vm.SongViewModel
import com.example.musicplayer.vm.SongViewModelFactory
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    val themePrefsKey = "theme"

    //    val appSettingPrefs: SharedPreferences = getSharedPreferences("AppSettingPrefs", 0)
//    var sharedPrefsEdit: SharedPreferences.Editor = appSettingPrefs.edit()
    private var isNightModeOn: Boolean = false


    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration

    private lateinit var toolBar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

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
        Log.d(TAG, "create")
        toolBar = binding.toolbar
        setSupportActionBar(toolBar)

        drawerLayout = binding.drawerLayout
        navigationView = binding.navView

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
            //navView.setupWithNavController(navController)
            navView.setNavigationItemSelectedListener(this@MainActivity)
        }


        viewModel.localSongs.observe(this) {
            Log.d(TAG, "local songs: ${it.toString()}")
        }

        // check permission
        checkPermission(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
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

    override fun onPause() {
        super.onPause()
        Log.d(LOG, "pause")
        val sharedPref =
            getSharedPreferences(themePrefsKey, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("NightMode", isNightModeOn)
            apply()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOG, "resume")
        val sharedPref = getSharedPreferences(themePrefsKey, Context.MODE_PRIVATE)
        with(sharedPref) {
            isNightModeOn = this.getBoolean("NightMode", false)
        }

        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            return
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            return
        }

    }

    //update local song between room and device
    private fun updateLocalSongs() {
        lifecycleScope.launch {
            delay(500L)
            viewModel.updateLocalSongs()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

    //3
    // Function to check and request permission.
    private fun checkPermission(permission: Array<String>, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                permission[0]
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dark_light_mode -> {
                isNightModeOn = if (isNightModeOn) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    false
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    true
                }
            }
            R.id.settingFragment -> {

            }
            else -> {

            }
        }

        return true
    }


}