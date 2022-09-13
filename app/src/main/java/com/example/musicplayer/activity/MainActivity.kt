package com.example.musicplayer.activity


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.musicplayer.R
import com.example.musicplayer.databinding.ActivityMainBinding
import com.example.musicplayer.model.Song
import com.example.musicplayer.utils.Contanst
import com.example.musicplayer.utils.Status
import com.example.musicplayer.vm.SongViewModel
import com.example.musicplayer.vm.WorkViewModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var sharedpreferences: SharedPreferences
    private val SHARED_PREFS = "shared_prefs"
    private val workViewModel: WorkViewModel by viewModels()

    private lateinit var toolBar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private val themePrefsKey = "theme"

    private var checkOn: Boolean = true

    //   // private val viewModel: HomeViewModel by viewModels()
    private val songViewModel: SongViewModel by viewModels()

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


        ///Không xoá
//        val sharedPref = getSharedPreferences(themePrefsKey, Context.MODE_PRIVATE)
//        val isNightMode = sharedPref.getBoolean("NightMode", false)
//
//        if (isNightMode) {
//            Log.d(LOG, "CREATE n")
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        } else {
//            Log.d(LOG, "CREATE l")
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }
////////////////////////////////////////////////Không xoá////////////////////////


        toolBar = binding.toolbar

        setSupportActionBar(toolBar)
        drawerLayout = binding.drawerLayout
        navigationView = binding.navView

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHost.navController

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Đồng cấp
        appBarConfig = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.searchFragment,
                R.id.rankFragment,
                R.id.libraryFragment,
            ), binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfig)

        binding.apply {
            bnvMain.setupWithNavController(navController)
            //navView.setupWithNavController(navController)
            navView.setNavigationItemSelectedListener(this@MainActivity)
        }

        songViewModel.localSongs.observe(this) {
            Log.d(TAG, "local songs: ${it.toString()}")
        }


        // check permission
        try {
            checkPermission(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                READ_STORAGE_PERMISSION_CODE
            )
        } catch (e: Exception) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (!Environment.isExternalStorageManager()) {
                    try {
                        val i = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                        i.addCategory("android.intent.category.DEFAULT")
                        i.data =
                            Uri.parse(String.format("package:%s", applicationContext.packageName))
                        storageActivityResultLauncher.launch(i)
                    } catch (e: Exception) {
                        val i = Intent()
                        i.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                        storageActivityResultLauncher.launch(i)
                    }
                    updateLocalSongs()
                    updateApiSongs()
                } else {
                    //Permission already granted
                    updateLocalSongs()
                    updateApiSongs()
                }
            }
        }

    }


    //update local song between room and device
    private fun updateLocalSongs() {
        lifecycleScope.launch {
            delay(500L)
            songViewModel.updateLocalSongs()
        }

    }

    private fun updateApiSongs() {
        lifecycleScope.launch {
            songViewModel.getSong().observe(this@MainActivity) {
                it?.let {
                    when (it.status) {
                        Status.SUCCESS -> {
                            val songs = it.data
                            Log.d(LOG, "Main $songs")
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
                                this@MainActivity.songViewModel.insertSongToDB(song)
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
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

    private var storageActivityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        // perform action when allow permission success
                        Log.d(Contanst.TAG, "granted")
                        updateLocalSongs()

                    } else {
                        Log.d(Contanst.TAG, "Denied")
                    }
                }
            }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dark_light_mode -> {
                startActivity(Intent(this, DarkLightModeActivity::class.java))
            }
            R.id.nav_profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
            }
            R.id.settingFragment -> {
                startActivity(Intent(this, SettingActivity::class.java))
            }
            else -> {

            }
        }
        return true
    }


}