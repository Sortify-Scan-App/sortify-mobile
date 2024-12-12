package com.dicoding.sortify.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recommendations")
data class RecommendationItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val link: String?,
    val title: String?,
    val historyId: Long
)