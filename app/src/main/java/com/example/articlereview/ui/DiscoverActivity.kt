package com.example.articlereview.ui

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.articlereview.adapter.ArticleAdapter
import com.example.articlereview.databinding.ActivityDiscoverBinding
import com.example.articlereview.model.ArticleDataSource
import com.example.articlereview.model.ArticleReview
import com.example.articlereview.model.DatabaseHelper
import com.google.android.material.chip.Chip

class DiscoverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiscoverBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: ArticleAdapter
    
    private var allArticles: List<ArticleReview> = emptyList()
    private var selectedTopic: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiscoverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        setupNavBar()
        setupRecyclerView()
        loadData()
        setupSearch()
    }

    private fun setupRecyclerView() {
        binding.rvDiscoverResults.layoutManager = LinearLayoutManager(this)
        adapter = ArticleAdapter(emptyList()) { article ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_ARTICLE, article)
            }
            startActivity(intent)
        }
        binding.rvDiscoverResults.adapter = adapter
    }

    private fun loadData() {
        Thread {
            val localArticles = dbHelper.getAllReviews()
            val sampleArticles = ArticleDataSource.getSampleArticles()
            allArticles = localArticles + sampleArticles
            
            // Extract unique topics
            val topics = allArticles.map { it.coverTag }.distinct().sorted()

            runOnUiThread {
                adapter.updateData(allArticles)
                setupChips(topics)
            }
        }.start()
    }

    private fun setupChips(topics: List<String>) {
        binding.chipGroupTopics.removeAllViews()

        // "All" chip
        val allChip = createChip("All")
        allChip.isChecked = true
        allChip.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedTopic = null
                filterArticles()
            }
        }
        binding.chipGroupTopics.addView(allChip)

        // Topic chips
        for (topic in topics) {
            val chip = createChip(topic)
            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedTopic = topic
                    filterArticles()
                }
            }
            binding.chipGroupTopics.addView(chip)
        }
    }

    private fun createChip(text: String): Chip {
        return Chip(this).apply {
            this.text = text
            isCheckable = true
            isClickable = true
            chipBackgroundColor = ColorStateList.valueOf(Color.parseColor("#F0F0F0"))
            checkedIconTint = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
            setTextColor(ColorStateList(
                arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()),
                intArrayOf(Color.WHITE, Color.parseColor("#1A1A1A"))
            ))
            setOnCheckedChangeListener { buttonView, isChecked ->
                val chipView = buttonView as Chip
                if (isChecked) {
                    chipView.chipBackgroundColor = ColorStateList.valueOf(Color.parseColor("#6B9E78"))
                } else {
                    chipView.chipBackgroundColor = ColorStateList.valueOf(Color.parseColor("#F0F0F0"))
                }
            }
        }
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            
            override fun afterTextChanged(s: Editable?) {
                filterArticles()
            }
        })
    }

    private fun filterArticles() {
        val query = binding.etSearch.text.toString().trim().lowercase()
        
        var filteredList = allArticles

        // 1. Filter by Topic
        if (selectedTopic != null) {
            filteredList = filteredList.filter { it.coverTag == selectedTopic }
        }

        // 2. Filter by Search Query (Title or Author)
        if (query.isNotEmpty()) {
            filteredList = filteredList.filter {
                it.title.lowercase().contains(query) || it.author.lowercase().contains(query)
            }
        }

        adapter.updateData(filteredList)
    }

    private fun setupNavBar() {
        binding.navHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
        binding.navLibrary.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
        binding.navProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    }
}
