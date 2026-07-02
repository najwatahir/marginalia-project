package com.example.articlereview.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.articlereview.adapter.ArticleAdapter
import com.example.articlereview.adapter.TrendingAdapter
import com.example.articlereview.databinding.ActivityHomeBinding
import com.example.articlereview.model.ArticleDataSource
import com.example.articlereview.model.DatabaseHelper

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        setupNavBar()
        setupRecyclerViews()
    }

    override fun onResume() {
        super.onResume()
        loadProfileHeader()
    }

    private fun loadProfileHeader() {
        val prefs = getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        val name = prefs.getString("name", "Marginalia Reader")
        val avatarPath = prefs.getString("avatarUri", null)

        binding.tvHomeName.text = name

        if (avatarPath != null) {
            try {
                val source: Any = if (avatarPath.startsWith("/")) {
                    java.io.File(avatarPath)
                } else {
                    Uri.parse(avatarPath)
                }
                Glide.with(this)
                    .load(source)
                    .transform(CircleCrop())
                    .into(binding.ivHomeAvatar)
                binding.ivHomeAvatar.setPadding(0, 0, 0, 0)
                binding.ivHomeAvatar.imageTintList = null
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setupRecyclerViews() {
        // Setup Trending (Horizontal)
        binding.rvTrending.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        
        // Setup Community (Vertical)
        binding.rvCommunity.layoutManager = LinearLayoutManager(this)

        Thread {
            val localArticles = dbHelper.getAllReviews()
            val sampleArticles = ArticleDataSource.getSampleArticles()
            
            // Mock trending: Highest rated samples
            val trending = sampleArticles.sortedByDescending { it.rating }.take(5)
            
            // Mock community: Mix of local and samples, randomized or just all
            val community = (localArticles + sampleArticles).shuffled().take(10)

            runOnUiThread {
                binding.rvTrending.adapter = TrendingAdapter(trending) { article ->
                    val intent = Intent(this, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_ARTICLE, article)
                    }
                    startActivity(intent)
                }

                binding.rvCommunity.adapter = ArticleAdapter(community) { article ->
                    val intent = Intent(this, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.EXTRA_ARTICLE, article)
                    }
                    startActivity(intent)
                }
            }
        }.start()
    }

    private fun setupNavBar() {
        binding.navDiscover.setOnClickListener {
            val intent = Intent(this, DiscoverActivity::class.java)
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
