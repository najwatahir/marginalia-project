package com.example.articlereview.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.articlereview.model.ArticleReview
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StorageHelper(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("ArticlePrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveArticle(article: ArticleReview) {
        val currentList = getArticles().toMutableList()
        currentList.add(0, article) // Tambahkan di urutan teratas

        val jsonString = gson.toJson(currentList)
        prefs.edit().putString("SAVED_ARTICLES", jsonString).apply()
    }

    fun getArticles(): List<ArticleReview> {
        val jsonString = prefs.getString("SAVED_ARTICLES", null) ?: return emptyList()
        val type = object : TypeToken<List<ArticleReview>>() {}.type
        return gson.fromJson(jsonString, type)
    }
}