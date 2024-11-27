package com.dicoding.sortify.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.sortify.data.remote.response.ArticlesItem
import com.dicoding.sortify.data.remote.retrofit.ApiService

class ArticleRepository private constructor(
    private val apiService: ApiService
) {
    fun getArticle(): LiveData<List<ArticlesItem>> = liveData {
        try {
            val response = apiService.getArticles()
            response.articles?.let { emit(it.filterNotNull()) }
        } catch (e: Exception) {
            Log.e("ArticleRepository", "getArticle: ${e.message}")
            emit(emptyList())
        }
    }

    companion object {
        @Volatile
        private var instance: ArticleRepository? = null

        fun getInstance(apiService: ApiService): ArticleRepository {
            return instance ?: synchronized(this) {
                instance ?: ArticleRepository(apiService).also { instance = it }
            }
        }
    }
}
