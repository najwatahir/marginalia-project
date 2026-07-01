package com.example.articlereview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.articlereview.R
import com.example.articlereview.model.ArticleReview

class TrendingAdapter(
    private val articles: List<ArticleReview>,
    private val onClick: (ArticleReview) -> Unit
) : RecyclerView.Adapter<TrendingAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTag: TextView = view.findViewById(R.id.tvTrendingTag)
        val tvTitle: TextView = view.findViewById(R.id.tvTrendingTitle)
        val tvReviewer: TextView = view.findViewById(R.id.tvTrendingReviewer)
        val tvRating: TextView = view.findViewById(R.id.tvTrendingRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trending_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.tvTag.text = article.coverTag.uppercase()
        holder.tvTitle.text = article.title
        
        // Mocking a reviewer name since our model just has author of the original article
        holder.tvReviewer.text = "By ${article.author}"
        holder.tvRating.text = article.rating.toString()

        holder.itemView.setOnClickListener { onClick(article) }
    }

    override fun getItemCount() = articles.size
}
