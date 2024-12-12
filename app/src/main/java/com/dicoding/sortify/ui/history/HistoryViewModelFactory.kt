package com.dicoding.sortify.ui.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.sortify.data.local.database.HistoryDatabase
import com.dicoding.sortify.data.repository.HistoryRepository

@Suppress("UNCHECKED_CAST")
class HistoryViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            val historyDao = HistoryDatabase.getDatabase(context).historyDao()
            val repository = HistoryRepository(historyDao)
            return HistoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
