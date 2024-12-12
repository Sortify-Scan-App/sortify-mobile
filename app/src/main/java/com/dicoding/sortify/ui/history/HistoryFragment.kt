package com.dicoding.sortify.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.sortify.adapter.FilterAdapter
import com.dicoding.sortify.adapter.HistoryAdapter
import com.dicoding.sortify.data.local.database.entity.HistoryWithRecommendations
import com.dicoding.sortify.databinding.DeleteDialogBinding
import com.dicoding.sortify.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var filterAdapter: FilterAdapter
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var binding: FragmentHistoryBinding
    private val historyViewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        setupFilterRecyclerView()
        setupHistoryRecyclerView()

        observeHistoryData()

        return binding.root
    }

    private fun setupFilterRecyclerView() {
        val filters = listOf("All", "Plastic", "Glass", "Paper", "Cardboard", "Metal", "Residue")
        filterAdapter = FilterAdapter(filters) { selectedFilter ->
            historyViewModel.loadHistory(selectedFilter)
        }
        binding.recyclerViewFilters.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = filterAdapter
        }
    }

    private fun setupHistoryRecyclerView() {
        historyAdapter = HistoryAdapter { historyWithRecommendations ->
            deleteHistory(historyWithRecommendations)
        }
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyAdapter
        }
    }

    private fun observeHistoryData() {
        historyViewModel.historyData.observe(viewLifecycleOwner) { historyList ->
            if (historyList.isEmpty()) {
                binding.lottieLoading.visibility = View.VISIBLE
                binding.tvEmptyDataMessage.visibility = View.VISIBLE
                binding.rvHistory.visibility = View.GONE
            } else {
                binding.lottieLoading.visibility = View.GONE
                binding.tvEmptyDataMessage.visibility = View.GONE
                binding.rvHistory.visibility = View.VISIBLE
                historyAdapter.submitList(historyList)
            }
        }
    }
    private fun deleteHistory(historyWithRecommendations: HistoryWithRecommendations) {
        val dialogBinding = DeleteDialogBinding.inflate(layoutInflater)
        val alertDialog = android.app.AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        dialogBinding.btnDelete.setOnClickListener {
            historyViewModel.deleteHistory(historyWithRecommendations.history.id.toLong())
            val updatedList = historyAdapter.currentList.toMutableList()
            updatedList.remove(historyWithRecommendations)
            historyAdapter.submitList(updatedList)
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

}
