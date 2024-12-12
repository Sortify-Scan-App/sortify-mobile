package com.dicoding.sortify.data.repository

import android.util.Log
import com.dicoding.sortify.data.remote.response.ArticlesItem
import com.dicoding.sortify.data.remote.retrofit.ApiService

class ArticleRepository private constructor(
    private val apiService: ApiService
) {
    suspend fun getArticle(): List<ArticlesItem> {
        return try {
            val response = apiService.getArticles()
            response.articles?.filterNotNull() ?: emptyList()
        } catch (e: Exception) {
            Log.e(TAG, "getArticles: ${e.message}", e)
            emptyList()
        }
    }

    companion object {
        @Volatile
        private var instance: ArticleRepository? = null
        private const val TAG = "ArticleRepository"

        fun getInstance(apiService: ApiService): ArticleRepository {
            return instance ?: synchronized(this) {
                instance ?: ArticleRepository(apiService).also { instance = it }
            }
        }
    }
}

