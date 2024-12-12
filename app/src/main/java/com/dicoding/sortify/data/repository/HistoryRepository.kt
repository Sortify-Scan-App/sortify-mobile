package com.dicoding.sortify.data.repository

import com.dicoding.sortify.data.local.database.HistoryDao
import com.dicoding.sortify.data.local.database.entity.HistoryWithRecommendations

class HistoryRepository(private val historyDao: HistoryDao) {

    suspend fun getAllHistories(): List<HistoryWithRecommendations> {
        return historyDao.getAllHistories()
    }

    suspend fun getHistoriesByWasteClass(wasteClass: String): List<HistoryWithRecommendations> {
        return historyDao.getHistoriesByWasteClass(wasteClass)
    }

    suspend fun deleteHistory(historyId: Long) {
        historyDao.deleteRecommendationsByHistoryId(historyId)
        historyDao.deleteHistoryById(historyId)
    }
}
