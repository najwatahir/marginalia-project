package com.example.articlereview.ui

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.articlereview.databinding.ActivityProfileBinding
import com.example.articlereview.model.ArticleDataSource
import com.example.articlereview.model.ArticleReview
import com.example.articlereview.model.DatabaseHelper
import com.google.android.material.chip.Chip
import com.google.android.material.progressindicator.LinearProgressIndicator

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var dbHelper: DatabaseHelper

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            saveProfileImage(it.toString())
            loadProfileImage(it.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        binding.btnBack.setOnClickListener { finish() }
        setupNavBar()
        setupEditProfile()
        loadProfileData()
    }
    
    private fun setupEditProfile() {
        val prefs = getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        val name = prefs.getString("name", "Marginalia Reader")
        val subtitle = prefs.getString("subtitle", "Avid Reader & Thinker")
        val avatarUri = prefs.getString("avatarUri", null)

        binding.tvProfileName.text = name
        binding.tvProfileSubtitle.text = subtitle
        avatarUri?.let { loadProfileImage(it) }

        binding.ivProfileAvatar.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        binding.llEditProfile.setOnClickListener {
            showEditProfileDialog()
        }
    }

    private fun loadProfileImage(uriString: String) {
        try {
            val uri = Uri.parse(uriString)
            Glide.with(this)
                .load(uri)
                .transform(CircleCrop())
                .into(binding.ivProfileAvatar)
            binding.ivProfileAvatar.setPadding(0, 0, 0, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveProfileImage(uriString: String) {
        val prefs = getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        prefs.edit().putString("avatarUri", uriString).apply()
    }

    private fun showEditProfileDialog() {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 10)
        }

        val nameInput = EditText(this).apply {
            hint = "Name"
            setText(binding.tvProfileName.text)
        }
        val bioInput = EditText(this).apply {
            hint = "Bio"
            setText(binding.tvProfileSubtitle.text)
        }

        layout.addView(nameInput)
        layout.addView(bioInput)

        AlertDialog.Builder(this)
            .setTitle("Edit Profile")
            .setView(layout)
            .setPositiveButton("Save") { _, _ ->
                val newName = nameInput.text.toString()
                val newBio = bioInput.text.toString()
                
                binding.tvProfileName.text = newName
                binding.tvProfileSubtitle.text = newBio
                
                val prefs = getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
                prefs.edit()
                    .putString("name", newName)
                    .putString("subtitle", newBio)
                    .apply()
            }
            .setNegativeButton("Cancel", null)
            .show()
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
            finish()
        }
        binding.navDiscover.setOnClickListener {
            val intent = Intent(this, DiscoverActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    }

    private fun loadProfileData() {
        Thread {
            val dbArticles = dbHelper.getAllReviews()
            val sampleArticles = ArticleDataSource.getSampleArticles()
            val allArticles = dbArticles + sampleArticles

            runOnUiThread {
                displayStats(allArticles)
                displayTopicBreakdown(allArticles)
                displayMoods(allArticles)
                displayRecentActivity(allArticles)
            }
        }.start()
    }

    // ─── Stats ──────────────────────────────────────────────

    private fun displayStats(articles: List<ArticleReview>) {
        // Total articles
        binding.tvTotalArticles.text = articles.size.toString()

        // Average rating
        val avgRating = if (articles.isNotEmpty())
            articles.map { it.rating.toDouble() }.average() else 0.0
        binding.tvAvgRating.text = String.format("%.1f", avgRating)

        // Favorite topic (most articles)
        val favTopic = articles.groupBy { it.coverTag }
            .maxByOrNull { it.value.size }?.key ?: "N/A"
        binding.tvFavoriteTopic.text = favTopic

        // Reading streak (total collection count)
        binding.tvStreakCount.text = articles.size.toString()
        binding.tvStreakLabel.text = "articles in your collection"
    }

    // ─── Articles by Topic ──────────────────────────────────

    private fun displayTopicBreakdown(articles: List<ArticleReview>) {
        val topicCounts = articles.groupBy { it.coverTag }
            .mapValues { it.value.size }
            .toList()
            .sortedByDescending { it.second }

        val maxCount = topicCounts.maxOfOrNull { it.second } ?: 1

        binding.topicContainer.removeAllViews()

        for ((topic, count) in topicCounts) {
            // Row container
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { bottomMargin = 16.dpToPx() }
            }

            // Header: topic name + count
            val header = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

            val topicName = TextView(this).apply {
                text = topic
                textSize = 14f
                setTextColor(Color.parseColor("#1A1A1A"))
                layoutParams = LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
                )
            }

            val countText = TextView(this).apply {
                text = count.toString()
                textSize = 14f
                setTextColor(Color.parseColor("#6B9E78"))
                typeface = Typeface.DEFAULT_BOLD
            }

            header.addView(topicName)
            header.addView(countText)

            // Progress bar
            val progressBar = LinearProgressIndicator(this@ProfileActivity).apply {
                max = maxCount
                setProgressCompat(count, false)
                trackColor = Color.parseColor("#E8EDE9")
                setIndicatorColor(Color.parseColor("#6B9E78"))
                trackCornerRadius = 4.dpToPx()
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { topMargin = 6.dpToPx() }
            }

            row.addView(header)
            row.addView(progressBar)
            binding.topicContainer.addView(row)
        }
    }

    // ─── Reading Moods ──────────────────────────────────────

    private fun displayMoods(articles: List<ArticleReview>) {
        val moods = articles.map { it.reviewerMood }.distinct()

        binding.chipGroupMoods.removeAllViews()

        for (mood in moods) {
            val chip = Chip(this).apply {
                text = mood
                isClickable = false
                isCheckable = false
                chipBackgroundColor = ColorStateList.valueOf(Color.parseColor("#E8F5E9"))
                setTextColor(Color.parseColor("#2E7D43"))
                chipStrokeColor = ColorStateList.valueOf(Color.parseColor("#6B9E78"))
                chipStrokeWidth = 1f * resources.displayMetrics.density
                textSize = 13f
            }
            binding.chipGroupMoods.addView(chip)
        }
    }

    // ─── Recent Activity ────────────────────────────────────

    private fun displayRecentActivity(articles: List<ArticleReview>) {
        val recent = articles.take(5)

        binding.recentContainer.removeAllViews()

        for (article in recent) {
            val itemLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { bottomMargin = 16.dpToPx() }
            }

            val emoji = TextView(this).apply {
                text = "📖"
                textSize = 18f
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { marginEnd = 12.dpToPx() }
            }

            val textLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
                )
            }

            val titleText = TextView(this).apply {
                text = article.title
                textSize = 14f
                setTextColor(Color.parseColor("#1A1A1A"))
                typeface = Typeface.DEFAULT_BOLD
                maxLines = 1
                ellipsize = TextUtils.TruncateAt.END
            }

            val metaText = TextView(this).apply {
                text = "by ${article.author} · ${article.dateRead}"
                textSize = 12f
                setTextColor(Color.parseColor("#A0A0A0"))
            }

            textLayout.addView(titleText)
            textLayout.addView(metaText)

            itemLayout.addView(emoji)
            itemLayout.addView(textLayout)
            binding.recentContainer.addView(itemLayout)
        }
    }

    // ─── Utility ────────────────────────────────────────────

    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()
}
