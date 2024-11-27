package com.dicoding.sortify.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.sortify.data.remote.response.ArticlesItem
import com.dicoding.sortify.data.repository.ArticleRepository
import kotlinx.coroutines.launch

class ArticleViewModel(private val articleRepository: ArticleRepository) : ViewModel() {
    fun getArticles(): LiveData<List<ArticlesItem>> {
        return articleRepository.getArticle()
    }
}

