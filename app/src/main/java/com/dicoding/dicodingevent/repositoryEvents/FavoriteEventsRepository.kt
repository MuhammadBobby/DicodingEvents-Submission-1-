package com.dicoding.dicodingevent.repositoryEvents

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.dicodingevent.database.FavoriteEvents
import com.dicoding.dicodingevent.database.FavoriteEventsDao
import com.dicoding.dicodingevent.database.FavoriteEventsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteEventsRepository(application: Application) {
    private val favoriteEventsDao: FavoriteEventsDao

    // Initialization
    init {
        val databaseEvent = FavoriteEventsDatabase.getDatabaseFavorite(application)
        favoriteEventsDao = databaseEvent.favoriteEventsDao()
    }

    // Get all favorite events
    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvents>> = favoriteEventsDao.getAllFavoriteEvents()

    // Get favorite event by ID
    fun getFavoriteEventById(id: Int): LiveData<FavoriteEvents> = favoriteEventsDao.getFavoriteEventById(id)

    // Insert favorite event (using coroutine)
    suspend fun insertEventFavorite(event: FavoriteEvents) {
        withContext(Dispatchers.IO) {
            favoriteEventsDao.insertEventFavorite(event)
        }
    }

    // Delete favorite event (using coroutine)
    suspend fun deleteEventFavorite(favoriteEvent: FavoriteEvents) {
        withContext(Dispatchers.IO) {
            favoriteEventsDao.deleteEventFavorite(favoriteEvent)
        }
    }
}
