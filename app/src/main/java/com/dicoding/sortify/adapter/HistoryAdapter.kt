package com.dicoding.sortify.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.sortify.data.local.database.entity.HistoryWithRecommendations
import com.dicoding.sortify.databinding.ItemHistoryBinding
import com.dicoding.sortify.helper.DateFormat

class HistoryAdapter(private val onDeleteClick: (HistoryWithRecommendations) -> Unit) : ListAdapter<HistoryWithRecommendations, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyWithRecommendations = getItem(position)
        holder.bind(historyWithRecommendations)
    }

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(historyWithRecommendations: HistoryWithRecommendations) {
            val history = historyWithRecommendations.history
            binding.predictionText.text = history.wasteClass
            binding.scoreText.text = history.confidenceScore
            history.imageUri?.let {
                binding.imageView.setImageURI(it.toUri())
            }
            binding.dateText.text = history.date?.let { DateFormat.formatDate(it) }
            binding.trashBtn.setOnClickListener {
                onDeleteClick(historyWithRecommendations)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryWithRecommendations>() {
            override fun areItemsTheSame(oldItem: HistoryWithRecommendations, newItem: HistoryWithRecommendations): Boolean {
                return oldItem.history.id == newItem.history.id
            }

            override fun areContentsTheSame(oldItem: HistoryWithRecommendations, newItem: HistoryWithRecommendations): Boolean {
                return oldItem == newItem
            }
        }
    }
}
