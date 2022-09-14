package com.example.musicplayer.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.musicplayer.databinding.ActivitySettingBinding
import com.example.musicplayer.utils.Contanst
import com.example.musicplayer.vm.SongViewModel
import com.example.musicplayer.vm.WorkViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val songViewModel: SongViewModel by viewModels()
    private val workViewModel: WorkViewModel by viewModels()

    @Inject
    lateinit var prefs: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val check = prefs.getBoolean("noti", false)
        if (check) binding.switchBtn.isChecked = true
        binding.switchBtn.setOnClickListener() {
            val editor = prefs.edit()
            if (binding.switchBtn.isChecked) {
                //reminder play song

                editor.putBoolean("noti", true)

                songViewModel.songs.observe(this) {
                    workViewModel.enqueuePeriodicReminder(it)
                }
                val reminder = intent.getStringExtra("reminder")
                Log.d(Contanst.TAG, "reminder: $reminder")
                if (reminder != null) {
                    songViewModel.getSongByName(reminder)
                    songViewModel.songByName.observe(this) {
                        Log.d(Contanst.TAG, "play s: ${it.toString()}")
                        //play music
                        /*val intent = Intent(this, MusicPlayerActivity::class.java)
                        intent.putExtra("song", it)
                        startActivity(intent)*/
                        val intentSong = Intent(this, MusicPlayerActivity::class.java)

                        val bundle = Bundle()
                        //bundle.putInt("pos", pos)
                        bundle.putInt("idSong", it.idSong!!)
                        bundle.putString("list", "listRankSong1")
                        intentSong.putExtras(bundle)
                        startActivity(intentSong)
                    }
                }
            } else {
                workViewModel.cancel()
                editor.putBoolean("noti", false)

            }
            editor.apply()
        }

    }
}