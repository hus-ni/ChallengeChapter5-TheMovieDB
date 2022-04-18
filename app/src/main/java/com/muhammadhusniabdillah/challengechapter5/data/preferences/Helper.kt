package com.muhammadhusniabdillah.challengechapter5.data.preferences

import android.content.Context
import android.content.SharedPreferences

class Helper(context: Context) {

    private val sharedPrefFile = "userPreference"
    private var sharedPref: SharedPreferences =
        context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPref.edit()

    fun putName(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    fun getName(key: String): String? {
        return sharedPref.getString(key, null)
    }

    fun putLoginStatus(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    fun getLoginStatus(key: String): Boolean {
        return sharedPref.getBoolean(key, false)
    }

    fun clear() {
        editor.clear().apply()
    }
}