package com.dicoding.dicodingevent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.dicodingevent.ui.settings.DarkModePreferences
import com.dicoding.dicodingevent.ui.settings.DarkModeViewModel

@Suppress("UNCHECKED_CAST")
class DarkModeViewModelFactory(private val pref: DarkModePreferences) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DarkModeViewModel::class.java)) {
            return DarkModeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}