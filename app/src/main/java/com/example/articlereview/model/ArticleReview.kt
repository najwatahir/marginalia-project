package com.example.articlereview.model

import java.io.Serializable

data class ArticleReview(
    val id: Int = 0,
    val title: String,
    val author: String,
    val source: String, 
    val sourceUrl: String,
    val coverTag: String,
    val readingTime: Int,
    val dateRead: String,
    val rating: Float,
    val shortSummary: String,
    val fullReview: String,
    val keyTakeaways: List<String>,
    val recommendedFor: String,
    val reviewerMood: String
) : Serializable
