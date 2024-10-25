package com.dicoding.dicodingevent.ui.favoriteEvent

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingevent.database.FavoriteEvents
import com.dicoding.dicodingevent.repositoryEvents.FavoriteEventsRepository
import kotlinx.coroutines.launch

class FavoriteEventViewModel(application: Application) : ViewModel() {
    private val favoriteEventsRepository: FavoriteEventsRepository = FavoriteEventsRepository(application)

    // Get all favorite events
    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvents>> = favoriteEventsRepository.getAllFavoriteEvents()

    // Get favorite event by ID
    fun getFavoriteEventById(id: Int): LiveData<FavoriteEvents> = favoriteEventsRepository.getFavoriteEventById(id)

    // Insert favorite event using coroutine
    fun insertEventFavorite(event: FavoriteEvents) {
        viewModelScope.launch {
            favoriteEventsRepository.insertEventFavorite(event)
        }
    }

    // Delete favorite event using coroutine
    fun deleteEventFavorite(favoriteEvent: FavoriteEvents) {
        viewModelScope.launch {
            favoriteEventsRepository.deleteEventFavorite(favoriteEvent)
        }
    }
}
