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
                tvSource.text = article.source
                tvTag.text = article.coverTag
                tvDate.text = article.dateRead
                tvReadingTime.text = "${article.readingTime} min read"
                tvSummary.text = article.shortSummary
                tvRating.text = String.format("%.1f", article.rating)
                tvMood.text = article.reviewerMood
                ratingBar.rating = article.rating

                // Source color accent
                val tagColor = getTagColor(article.coverTag)
                tvTag.setTextColor(tagColor)
                cardTagAccent.setCardBackgroundColor(tagColor)

                root.setOnClickListener { onItemClick(article) }
            }
        }

        private fun getTagColor(tag: String): Int {
            val context = binding.root.context
            return when (tag) {
                "Philosophy" -> context.getColor(com.example.articlereview.R.color.tag_philosophy)
                "Psychology" -> context.getColor(com.example.articlereview.R.color.tag_psychology)
                "Wellbeing" -> context.getColor(com.example.articlereview.R.color.tag_wellbeing)
                "Economics" -> context.getColor(com.example.articlereview.R.color.tag_economics)
                "Science" -> context.getColor(com.example.articlereview.R.color.tag_science)
                "Lifestyle" -> context.getColor(com.example.articlereview.R.color.tag_lifestyle)
                "Technology" -> context.getColor(com.example.articlereview.R.color.tag_technology)
                else -> context.getColor(com.example.articlereview.R.color.accent_green)
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
