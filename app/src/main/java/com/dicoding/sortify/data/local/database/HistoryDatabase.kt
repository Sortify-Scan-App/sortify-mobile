package com.dicoding.sortify.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.sortify.data.local.database.entity.HistoryEntity
import com.dicoding.sortify.data.local.database.entity.RecommendationItemEntity

@Database(entities = [HistoryEntity::class, RecommendationItemEntity::class], version = 2)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: HistoryDatabase? = null

        fun getDatabase(context: Context): HistoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDatabase::class.java,
                    "history_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
