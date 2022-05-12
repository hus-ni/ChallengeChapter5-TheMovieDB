package com.muhammadhusniabdillah.challengechapter5.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreferences(private val context: Context) {

    companion object {
        private const val DATASTORE_NAME = "login_session"

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

        private val EMAIL = stringPreferencesKey("email")

        private val SESSION = stringPreferencesKey("is_login")

        const val LOGGED_IN = "logged_in"
        const val NOT_LOGGED_IN = "not_logged_in"
    }

    suspend fun saveSession(isLogin: String) {
        context.dataStore.edit {
            it[SESSION] = isLogin
        }
    }

    fun getSession(): Flow<String?> {
        return context.dataStore.data.map {
            it[SESSION]
        }
    }

    suspend fun saveEmail(email: String) {
        context.dataStore.edit {
            it[EMAIL] = email
        }
    }

    fun getEmail(): Flow<String?> {
        return context.dataStore.data.map {
            it[EMAIL]
        }
    }
}