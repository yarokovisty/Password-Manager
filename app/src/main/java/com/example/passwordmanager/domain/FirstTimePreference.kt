package com.example.passwordmanager.domain

import android.content.Context
import android.content.SharedPreferences

class FirstTimePreference(context: Context) {

    private val preference: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var isFirstTime: Boolean
        get() = preference.getBoolean(FIRST_TIME_KEY, true)
        set(value) {
            preference.edit().putBoolean(FIRST_TIME_KEY, value).apply()
        }

    companion object {
        private const val PREFS_NAME = "MyPrefs"
        private const val FIRST_TIME_KEY = "FirstTime"
    }
}