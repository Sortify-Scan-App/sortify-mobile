package com.dicoding.sortify.ui.article

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.sortify.databinding.ActivityArticleBinding
import com.dicoding.sortify.adapter.ArticleAdapter
import com.dicoding.sortify.data.remote.retrofit.ApiConfig
import com.dicoding.sortify.data.repository.ArticleRepository

class ArticleActivity : AppCompatActivity() {

    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var binding: ActivityArticleBinding

    private val articleViewModel: ArticleViewModel by viewModels {
        ArticleViewModelFactory(ArticleRepository.getInstance(ApiConfig.getApiService()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rvArticles = binding.rvArticles
        rvArticles.layoutManager = LinearLayoutManager(this)

        articleAdapter = ArticleAdapter()
        rvArticles.adapter = articleAdapter

        articleViewModel.getArticles()

        articleViewModel.articles.observe(this) { articles ->
            if (articles != null) {
                articleAdapter.submitList(articles)
            }
        }

        articleViewModel.loading.observe(this) { isLoading ->
            binding.lottieLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        binding.imgBack.setOnClickListener {
            finish()
        }
    }

}

