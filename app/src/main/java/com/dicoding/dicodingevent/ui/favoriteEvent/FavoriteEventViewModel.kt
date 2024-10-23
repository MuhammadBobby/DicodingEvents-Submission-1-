package com.dicoding.dicodingevent.ui.favoriteEvent

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dicodingevent.database.FavoriteEvents
import com.dicoding.dicodingevent.repositoryEvents.FavoriteEventsRepository

class FavoriteEventViewModel(application: Application) : ViewModel() {
    //initialization repository
    private val favoriteEventsRepository: FavoriteEventsRepository = FavoriteEventsRepository(application)

    //get all favorite events
    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvents>> = favoriteEventsRepository.getAllFavoriteEvents()

    //get favorite event by id
    fun getFavoriteEventById(id: String): LiveData<FavoriteEvents> = favoriteEventsRepository.getFavoriteEventById(id)

    //insert favorite event
    fun insertEventFavorite(event: FavoriteEvents) {
        favoriteEventsRepository.insertEventFavorite(event)
    }

    //delete favorite event
    fun deleteEventFavorite(favoriteEvent: FavoriteEvents) {
        favoriteEventsRepository.deleteEventFavorite(favoriteEvent)
    }

}