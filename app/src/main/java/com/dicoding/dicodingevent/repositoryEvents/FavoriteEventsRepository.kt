package com.dicoding.dicodingevent.repositoryEvents

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.dicodingevent.database.FavoriteEvents
import com.dicoding.dicodingevent.database.FavoriteEventsDao
import com.dicoding.dicodingevent.database.FavoriteEventsDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteEventsRepository(application: Application) {
    private val favoriteEventsDao: FavoriteEventsDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    //initialisation repository
    init {
        val databaseEvent = FavoriteEventsDatabase.getDatabaseFavorite(application)
        favoriteEventsDao = databaseEvent.favoriteEventsDao()
    }

    //init all fun from dao
    //get all favorite events
    fun getAllFavoriteEvents() : LiveData<List<FavoriteEvents>> = favoriteEventsDao.getAllFavoriteEvents()

    //get favorite event by id
    fun getFavoriteEventById(id: Int) : LiveData<FavoriteEvents> = favoriteEventsDao.getFavoriteEventById(id)

    //insert favorite event
    fun insertEventFavorite(event: FavoriteEvents) {
        executorService.execute { favoriteEventsDao.insertEventFavorite(event) }
    }

    //delete favorite event
    fun deleteEventFavorite(favoriteEvent: FavoriteEvents) {
        executorService.execute { favoriteEventsDao.deleteEventFavorite(favoriteEvent) }
    }
}