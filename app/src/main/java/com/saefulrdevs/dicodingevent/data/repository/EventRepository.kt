package com.saefulrdevs.dicodingevent.data.repository

import android.util.Log
import com.saefulrdevs.dicodingevent.data.database.FavoriteEventDao
import com.saefulrdevs.dicodingevent.data.model.FavoriteEvent

class EventRepository(private val favoriteEventDao: FavoriteEventDao) {
    suspend fun insertFavoriteEvent(event: FavoriteEvent): Boolean {
        return try {
            val result = favoriteEventDao.insertFavoriteEvent(event)
            Log.d("Insert EventRepository", "Insert Favorite Event: $result")
            result != -1L
            true
        } catch (e: Exception) {
            Log.e("Insert EventRepository", "Error inserting favorite event: ${e.message}")
            false
        }
    }

    suspend fun deleteFavoriteEvent(event: FavoriteEvent): Boolean {
        return try {
            favoriteEventDao.deleteFavoriteEvent(event)
            Log.d("Delete EventRepository", "Delete Favorite Event: $event")
            true
        } catch (e: Exception) {
            Log.e("Delete EventRepository", "Error deleting favorite event: ${e.message}")
            false
        }
    }


}