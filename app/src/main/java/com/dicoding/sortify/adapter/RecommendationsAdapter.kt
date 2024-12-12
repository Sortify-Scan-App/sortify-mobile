package com.dicoding.sortify.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.sortify.data.remote.response.RecommendationsItem
import com.dicoding.sortify.databinding.ItemRecommendationBinding

class RecommendationsAdapter(
    private val recommendations: List<RecommendationsItem>
) : RecyclerView.Adapter<RecommendationsAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recommendation: RecommendationsItem) {
            binding.tvRecommendationTitle.text = recommendation.title
            binding.root.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(recommendation.link))
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecommendationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recommendations[position])
    }

    override fun getItemCount(): Int = recommendations.size
}
