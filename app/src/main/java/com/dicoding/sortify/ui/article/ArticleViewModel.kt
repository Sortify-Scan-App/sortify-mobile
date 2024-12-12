package com.dicoding.sortify.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.sortify.data.remote.response.ArticlesItem
import com.dicoding.sortify.data.repository.ArticleRepository
import kotlinx.coroutines.launch

class ArticleViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _articles = MutableLiveData<List<ArticlesItem>>()
    val articles: LiveData<List<ArticlesItem>> get() = _articles

    fun getArticles() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val articleList = articleRepository.getArticle()
                _articles.postValue(articleList)
            } catch (e: Exception) {
                _articles.postValue(emptyList())
            } finally {
                _loading.value = false
            }
        }
    }
}

