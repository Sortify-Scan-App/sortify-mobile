package com.dicoding.sortify.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.sortify.adapter.FilterAdapter
import com.dicoding.sortify.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var filterAdapter: FilterAdapter
    private lateinit var recyclerViewFilters: RecyclerView
    private lateinit var tvFilterResult: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHistoryBinding.inflate(inflater, container, false)

        recyclerViewFilters = binding.recyclerViewFilters
        tvFilterResult = binding.tvFilterResult

        // Menyiapkan RecyclerView dengan FilterAdapter
        recyclerViewFilters.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val filters = listOf("All", "Plastic", "Glass", "Paper", "Board", "Metal", "Residue")

        filterAdapter = FilterAdapter(filters) { selectedFilter ->
            // Update langsung teks hasil filter tanpa memilih
            tvFilterResult.text = "Filter: $selectedFilter"
        }
        recyclerViewFilters.adapter = filterAdapter

        // Set default filter saat fragment pertama kali dimuat
        tvFilterResult.text = "Filter: All"

        return binding.root
    }
}
