package com.example.articlereview.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.articlereview.adapter.ArticleAdapter
import com.example.articlereview.databinding.ActivityMainBinding
import com.example.articlereview.model.ArticleDataSource
import com.example.articlereview.model.ArticleReview

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ArticleAdapter
    private var allArticles = ArticleDataSource.getSampleArticles()
    private var filteredArticles = allArticles.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupFilterSpinner()
        setupStats()
    }

    private fun setupRecyclerView() {
        adapter = ArticleAdapter(filteredArticles) { article ->
            openDetail(article)
        }
        binding.rvArticles.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setupFilterSpinner() {
        val tags = listOf("All Topics") + allArticles.map { it.coverTag }.distinct().sorted()
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tags)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFilter.adapter = spinnerAdapter

        binding.spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val selected = tags[pos]
                filterByTag(selected)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun filterByTag(tag: String) {
        filteredArticles.clear()
        filteredArticles.addAll(
            if (tag == "All Topics") allArticles
            else allArticles.filter { it.coverTag == tag }
        )
        adapter.notifyDataSetChanged()
        binding.tvReviewCount.text = "${filteredArticles.size} reviews"
    }

    private fun setupStats() {
        val avgRating = allArticles.map { it.rating }.average()
        val totalMinutes = allArticles.sumOf { it.readingTime }
        binding.tvReviewCount.text = "${allArticles.size} reviews"
        binding.tvAvgRating.text = String.format("%.1f avg", avgRating)
        binding.tvTotalTime.text = "${totalMinutes} min read"
    }

    private fun openDetail(article: ArticleReview) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ARTICLE, article)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
