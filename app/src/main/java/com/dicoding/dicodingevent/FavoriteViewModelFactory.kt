package com.dicoding.dicodingevent

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.dicodingevent.ui.favoriteEvent.FavoriteEventViewModel

@Suppress("UNCHECKED_CAST")
class FavoriteViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteEventViewModel::class.java)) {
            return FavoriteEventViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}