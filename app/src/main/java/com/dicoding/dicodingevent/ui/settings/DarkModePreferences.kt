package com.dicoding.dicodingevent.ui.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DarkModePreferences private constructor(private val dataStore: DataStore<Preferences>) {
    //setting datastore
    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[DARK_MODE_KEY] ?: false
        }
    }

    //menyimpan ke dalam datastore
    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences -> preferences[DARK_MODE_KEY] = isDarkModeActive }
    }

    //instance setting preferences
    companion object {
        @Volatile
        private var INSTANCE: DarkModePreferences ?= null

        fun getInstance(dataStore: DataStore<Preferences>):DarkModePreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = DarkModePreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}