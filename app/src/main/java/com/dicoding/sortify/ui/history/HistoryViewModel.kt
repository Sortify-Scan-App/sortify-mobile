package com.dicoding.sortify.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.sortify.data.local.database.entity.HistoryWithRecommendations
import com.dicoding.sortify.data.repository.HistoryRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {

    private val _historyData = MutableLiveData<List<HistoryWithRecommendations>>()
    val historyData: LiveData<List<HistoryWithRecommendations>> get() = _historyData

    init {
        loadHistory("All")
    }

    fun loadHistory(wasteClass: String) {
        viewModelScope.launch {
            val data = when (wasteClass) {
                "Plastic" -> repository.getHistoriesByWasteClass("Plastic")
                "Glass" -> repository.getHistoriesByWasteClass("Glass")
                "Paper" -> repository.getHistoriesByWasteClass("Paper")
                "Cardboard" -> repository.getHistoriesByWasteClass("Cardboard")
                "Metal" -> repository.getHistoriesByWasteClass("Metal")
                "Residue" -> repository.getHistoriesByWasteClass("Residue")
                else -> repository.getAllHistories()
            }
            _historyData.postValue(data)
        }
    }


    fun deleteHistory(historyId: Long) {
        viewModelScope.launch {
            repository.deleteHistory(historyId)
        }
    }

}


