package com.dicoding.sortify.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.sortify.R
import com.dicoding.sortify.data.remote.response.ArticlesItem
import com.dicoding.sortify.databinding.ItemArticleHomeBinding
import com.dicoding.sortify.ui.article.WebViewActivity


class HomeArticlesAdapter : ListAdapter<ArticlesItem, HomeArticlesAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemArticleHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

    class MyViewHolder(private val binding: ItemArticleHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: ArticlesItem) {
            binding.tvArticleTitle1.text = news.title?.let { truncateTitle(it) }
            Glide.with(itemView.context)
                .load(news.image)
                .error(R.drawable.ic_no_image)
                .into(binding.imgArticle1)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, WebViewActivity::class.java)
                intent.putExtra(WebViewActivity.EXTRA_URL, news.link)
                itemView.context.startActivity(intent)
            }


        }

        private fun truncateTitle(title: String): String {
            return if (title.length > 20) {
                "${title.substring(0, 20)}..."
            } else {
                title
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ArticlesItem> =
            object : DiffUtil.ItemCallback<ArticlesItem>() {
                override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                    return oldItem.title == newItem.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                    return oldItem == newItem
                }
            }
    }
}