package com.example.articlereview.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.articlereview.adapter.ArticleAdapter
import com.example.articlereview.databinding.ActivityMainBinding
import com.example.articlereview.model.ArticleDataSource
import com.example.articlereview.model.DatabaseHelper
import com.example.articlereview.utils.StorageHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var storageHelper: StorageHelper
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storageHelper = StorageHelper(this)
        dbHelper = DatabaseHelper(this)

        // Setup RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Tombol Tambah Review
        // Ini yang bikin tombolnya bisa di-klik dan pindah halaman
        binding.fabAddReview.setOnClickListener {
            val intent = Intent(this, AddReviewActivity::class.java)
            startActivity(intent)
        }
    }

    // Gunakan onResume agar list otomatis refresh setelah kembali dari AddReviewActivity
    override fun onResume() {
        super.onResume()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        Thread {
            val localArticles = dbHelper.getAllReviews()
            val defaultArticles = ArticleDataSource.getSampleArticles()
            val allArticles = localArticles + defaultArticles

            runOnUiThread {
                binding.tvStats.text = "You have ${allArticles.size} reviews in your library."
                val adapter = ArticleAdapter(allArticles) { article ->
                    val intent = Intent(this, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_ARTICLE, article)
                    }
                    startActivity(intent)
                }
                binding.recyclerView.adapter = adapter
            }
        }.start()
    }
}