package com.dicoding.dicodingevent.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEvents::class], version = 1)
abstract class FavoriteEventsDatabase : RoomDatabase() {
    abstract fun favoriteEventsDao(): FavoriteEventsDao

    companion object {
        @Volatile
        private var dbInstance: FavoriteEventsDatabase? = null

        //get database instance
        fun getDatabaseFavorite (context: Context): FavoriteEventsDatabase {
            if (dbInstance == null) {
                synchronized(FavoriteEventsDatabase::class.java) {
                    dbInstance = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteEventsDatabase::class.java,
                        "favorite_events_db")
                        .build()
            }
        }

            return dbInstance as FavoriteEventsDatabase
        }

    }

}