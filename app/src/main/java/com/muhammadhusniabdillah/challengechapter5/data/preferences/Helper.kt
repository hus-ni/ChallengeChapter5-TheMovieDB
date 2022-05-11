package com.muhammadhusniabdillah.challengechapter5.data.preferences

import android.content.Context
import android.content.SharedPreferences

object Helper {

    private lateinit var sharedPrefs: SharedPreferences
    private const val sharedPrefFile = "userPreference"
    fun init(context: Context) {
        sharedPrefs = context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
    }


    fun putLoginStatus(key: String, value: Boolean) {
        val editor: SharedPreferences.Editor = sharedPrefs.edit()
        editor.putBoolean(key, value).apply()
    }

    fun getLoginStatus(key: String): Boolean {
        return sharedPrefs.getBoolean(key, false)
    }

    fun putEmail(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPrefs.edit()
        editor.putString(key, value).apply()
    }

    fun getEmail(key: String): String? {
        return sharedPrefs.getString(key, null)
    }

    fun clear() {
        val editor: SharedPreferences.Editor = sharedPrefs.edit()
        editor.clear().apply()
    }
}