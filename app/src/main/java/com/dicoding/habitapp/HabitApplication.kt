package com.dicoding.habitapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.dicoding.habitapp.utils.DarkMode
import java.util.*

class HabitApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        preferences.getString(
            getString(R.string.pref_key_dark),
            getString(R.string.pref_dark_follow_system)
        )?.apply {
            val mode = DarkMode.valueOf(this.uppercase(Locale.US))
            AppCompatDelegate.setDefaultNightMode(mode.value)
        }
    }
}