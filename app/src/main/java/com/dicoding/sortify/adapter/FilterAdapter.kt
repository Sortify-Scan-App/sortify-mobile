package com.dicoding.sortify.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.sortify.R

class FilterAdapter(
    private val filters: List<String>,
    private val onFilterClick: (String) -> Unit
) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    private var selectedPosition: Int = 0

    inner class FilterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btnFilter: Button = view.findViewById(R.id.btnFilter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
        return FilterViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val filter = filters[position]
        holder.btnFilter.text = filter

        val isActive = holder.adapterPosition == selectedPosition

        holder.btnFilter.setBackgroundColor(
            if (isActive) ContextCompat.getColor(holder.itemView.context, R.color.md_theme_primary)
            else ContextCompat.getColor(holder.itemView.context, R.color.accent_color)
        )
        holder.btnFilter.setTextColor(
            if (isActive) Color.WHITE
            else ContextCompat.getColor(holder.itemView.context, R.color.custom_text)
        )

        holder.btnFilter.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = holder.adapterPosition

            if (previousPosition != selectedPosition) {
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
            }

            onFilterClick(filter) // Aksi klik tombol filter
        }
    }

    override fun getItemCount(): Int = filters.size
}

