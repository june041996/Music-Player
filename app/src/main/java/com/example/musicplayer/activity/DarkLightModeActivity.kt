package com.example.musicplayer.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.musicplayer.databinding.ActivityDarkLightModeBinding

class DarkLightModeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDarkLightModeBinding
    private val themePrefsKey = "theme"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDarkLightModeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val toolBar = binding.toolbar
        setSupportActionBar(toolBar)

        val sharedPref = getSharedPreferences(themePrefsKey, Context.MODE_PRIVATE)

        var isNightMode = sharedPref.getBoolean("NightMode", false)


        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        binding.switchDarkMode.setOnClickListener {
            if (isNightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
                with(sharedPref.edit()) {
                    putBoolean("NightMode", false)
                    apply()
                }

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
                with(sharedPref.edit()) {
                    putBoolean("NightMode", true)
                    apply()
                }
            }
        }
    }
}