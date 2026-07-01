package com.example.articlereview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.articlereview.databinding.ItemArticleReviewBinding
import com.example.articlereview.model.ArticleReview

class ArticleAdapter(
    private val articles: List<ArticleReview>,
    private val onItemClick: (ArticleReview) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(
        private val binding: ItemArticleReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticleReview) {
            binding.apply {
                tvTitle.text = article.title
                tvAuthor.text = article.author
                tvDate.text = article.dateRead
                tvSummary.text = article.shortSummary
                ratingBar.rating = article.rating

                root.setOnClickListener { onItemClick(article) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleReviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int = articles.size
}
