package com.example.articlereview.model

import java.io.Serializable

data class ArticleReview(
    val id: Int,
    val title: String,
    val author: String,
    val source: String,         // e.g. "Medium", "Substack", "Aeon"
    val sourceUrl: String,
    val coverTag: String,       // Topic tag e.g. "Technology", "Philosophy"
    val readingTime: Int,       // in minutes
    val dateRead: String,
    val rating: Float,          // 1.0 - 5.0
    val shortSummary: String,
    val fullReview: String,
    val keyTakeaways: List<String>,
    val recommendedFor: String,
    val reviewerMood: String    // e.g. "Thought-provoking", "Inspiring", "Dense"
) : Serializable
