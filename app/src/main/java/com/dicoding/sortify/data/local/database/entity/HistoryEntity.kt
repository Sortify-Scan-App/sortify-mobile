package com.dicoding.sortify.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val wasteClass: String?,
    val confidenceScore: String?,
    val imageUri: String?,
    val date: String?,
)


