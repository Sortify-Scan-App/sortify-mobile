package com.dicoding.sortify.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.sortify.R
import com.dicoding.sortify.adapter.HomeArticlesAdapter
import com.dicoding.sortify.data.AuthViewModelFactory
import com.dicoding.sortify.data.local.WasteData
import com.dicoding.sortify.data.remote.retrofit.ApiConfig
import com.dicoding.sortify.data.repository.ArticleRepository
import com.dicoding.sortify.databinding.FragmentHomeBinding
import com.dicoding.sortify.ui.article.ArticleActivity
import com.dicoding.sortify.ui.article.ArticleViewModel
import com.dicoding.sortify.ui.article.ArticleViewModelFactory
import com.dicoding.sortify.ui.point.PointActivity
import com.dicoding.sortify.ui.waste.DetailWasteActivity
import java.util.Calendar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeArticleAdapter: HomeArticlesAdapter
    private val homeViewModel: HomeViewModel by viewModels {
        AuthViewModelFactory.getInstance(requireContext())
    }
    private val articleViewModel: ArticleViewModel by viewModels {
        ArticleViewModelFactory(ArticleRepository.getInstance(ApiConfig.getApiService()))
    }

    private fun setupFabIcons() {
        WasteData.wasteList.forEachIndexed { index, waste ->
            val fab = when (index) {
                0 -> binding.fab1
                1 -> binding.fab2
                2 -> binding.fab3
                3 -> binding.fab4
                4 -> binding.fab5
                5 -> binding.fab6
                else -> return@forEachIndexed
            }

            fab.imageTintList = null

            fab.setOnClickListener {
                val intent = Intent(requireContext(), DetailWasteActivity::class.java).apply {
                    putExtra("NAME", waste.name)
                    putExtra("IMAGE", waste.imageResId)
                    putExtra("DESC", waste.description)
                    putExtra("FACT", waste.funFact)
                }
                startActivity(intent)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupFabIcons()

        homeViewModel.getSession().observe(viewLifecycleOwner) { user ->
            Log.d("HomeFragment", "User data: $user")
            if (user.isLogin) {
                binding.tvName.text = user.fullname
            }
        }

        // Greeting text
        binding.tvGreeting.text = getGreeting()

        // Setting up the redeem and see more buttons
        binding.tvRedeem.setOnClickListener {
            startActivity(Intent(requireContext(), PointActivity::class.java))
        }
        binding.tvSeeMore.setOnClickListener {
            startActivity(Intent(requireContext(), ArticleActivity::class.java))
        }

        // Setup RecyclerView for articles
        homeArticleAdapter = HomeArticlesAdapter()
        binding.rvArticles.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = homeArticleAdapter
        }

        // Observing article data
        articleViewModel.articles.observe(viewLifecycleOwner) { articles ->
            homeArticleAdapter.submitList(articles)
        }

        // Observing loading state
        articleViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.lottieLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Trigger to fetch articles
        articleViewModel.getArticles()

        return root
    }

    private fun getGreeting(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 5..11 -> getString(R.string.morning_greeting)
            in 12..14 -> getString(R.string.afternoon_greeting)
            in 15..17 -> getString(R.string.evening_greeting)
            else -> getString(R.string.night_greeting)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
