package com.dicoding.sortify.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.sortify.databinding.FragmentHomeBinding
import com.dicoding.sortify.ui.article.ArticleActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private fun setupFabIcons() {
        listOf(binding.fab1, binding.fab2, binding.fab3,
            binding.fab4, binding.fab5, binding.fab6)
            .forEach { it.imageTintList = null }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupFabIcons()

        binding.tvSeeMore.setOnClickListener{
            val intent = Intent(requireContext(), ArticleActivity::class.java)
            startActivity(intent)
        }
//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}