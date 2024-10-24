package com.dicoding.dicodingevent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteEventsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //Insert -> add data ke database
    fun insertEventFavorite(favoriteEvents: FavoriteEvents)

    @Delete
    //Delete -> delete data dari database
    fun deleteEventFavorite(favoriteEvents: FavoriteEvents)

    @Query("SELECT * FROM favoriteevents ORDER BY id ASC")
    //getAllFavoriteEvents -> select all data from database
    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvents>>

    //detail -> select data by id from database
    @Query("SELECT * FROM favoriteevents WHERE id = :id")
    fun getFavoriteEventById(id: Int): LiveData<FavoriteEvents>

}