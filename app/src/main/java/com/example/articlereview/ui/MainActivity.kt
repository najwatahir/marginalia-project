package com.example.articlereview.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.articlereview.adapter.ArticleAdapter
import com.example.articlereview.databinding.ActivityMainBinding
import com.example.articlereview.model.ArticleDataSource
import com.example.articlereview.utils.StorageHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var storageHelper: StorageHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storageHelper = StorageHelper(this)

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
        // Gabungkan data dari Local Storage dan Data Bawaan
        val localArticles = storageHelper.getArticles()
        val defaultArticles = ArticleDataSource.getSampleArticles() // Asumsi kamu sudah punya file data bawaan ini
        val allArticles = localArticles + defaultArticles

        val adapter = ArticleAdapter(allArticles) { article ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("EXTRA_ARTICLE", article)
            }
            startActivity(intent)
        }
        binding.recyclerView.adapter = adapter
    }
}