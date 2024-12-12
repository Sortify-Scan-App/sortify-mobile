package com.dicoding.sortify.data.local.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class HistoryWithRecommendations(
    @Embedded val history: HistoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "historyId"
    )
    val recommendations: List<RecommendationItemEntity>
)
