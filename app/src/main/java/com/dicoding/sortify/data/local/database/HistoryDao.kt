package com.dicoding.sortify.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.dicoding.sortify.data.local.database.entity.HistoryEntity
import com.dicoding.sortify.data.local.database.entity.RecommendationItemEntity
import com.dicoding.sortify.data.local.database.entity.HistoryWithRecommendations

@Dao
interface HistoryDao {
    @Insert
    suspend fun insertHistory(history: HistoryEntity): Long

    @Insert
    suspend fun insertRecommendation(recommendation: RecommendationItemEntity)

    @Transaction
    @Query("SELECT * FROM history WHERE id = :historyId")
    suspend fun getHistoryWithRecommendations(historyId: Long): HistoryWithRecommendations

    @Transaction
    @Query("SELECT * FROM history")
    suspend fun getAllHistories(): List<HistoryWithRecommendations>

    @Transaction
    @Query("SELECT * FROM history WHERE wasteClass = :wasteClass")
    suspend fun getHistoriesByWasteClass(wasteClass: String): List<HistoryWithRecommendations>

    // Hapus History berdasarkan ID
    @Query("DELETE FROM history WHERE id = :historyId")
    suspend fun deleteHistoryById(historyId: Long)

    // Hapus rekomendasi berdasarkan historyId
    @Query("DELETE FROM recommendations WHERE historyId = :historyId")
    suspend fun deleteRecommendationsByHistoryId(historyId: Long)

}
