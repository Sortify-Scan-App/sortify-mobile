package com.dicoding.sortify.ui.article

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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

        binding.imgBack.setOnClickListener {
            finish()
        }

        val rvArticles = binding.rvArticles
        rvArticles.layoutManager = LinearLayoutManager(this)

        articleAdapter = ArticleAdapter()
        rvArticles.adapter = articleAdapter

        articleViewModel.getArticles().observe(this, Observer { articles ->
            articleAdapter.submitList(articles)
        })
    }
}

