package com.example.articlereview.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.articlereview.databinding.ActivityAddReviewBinding
import com.example.articlereview.model.DatabaseHelper
import com.example.articlereview.model.ArticleReview
import com.example.articlereview.utils.StorageHelper
import android.view.View

class AddReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddReviewBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        binding.btnBack.setOnClickListener { finish() }

        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            binding.tvRatingValue.text = rating.toString()
        }

        val existingArticle = intent.getSerializableExtra(DetailActivity.EXTRA_ARTICLE) as? ArticleReview
        if (existingArticle != null) {
            // Edit Mode
            binding.tvTitleHeader.text = "Edit Review"
            binding.etTitle.setText(existingArticle.title)
            binding.etAuthor.setText(existingArticle.author)
            binding.etSource.setText(existingArticle.source)
            binding.etReadingDuration.setText(existingArticle.readingTime.toString())
            binding.etShortSummary.setText(existingArticle.shortSummary)
            binding.etFullReview.setText(existingArticle.fullReview)
            binding.ratingBar.rating = existingArticle.rating
            binding.etImport.setText(existingArticle.sourceUrl)
            
            binding.btnSubmit.text = "Update Review"
            binding.btnDelete.visibility = View.VISIBLE

            binding.btnDelete.setOnClickListener {
                Thread {
                    dbHelper.deleteReview(existingArticle.id)
                    runOnUiThread {
                        Toast.makeText(this, "Review deleted!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }.start()
            }
        }

        binding.btnSubmit.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val author = binding.etAuthor.text.toString().trim()
            val source = binding.etSource.text.toString().trim()
            val readingDuration = binding.etReadingDuration.text.toString().trim()
            val shortSummary = binding.etShortSummary.text.toString().trim()
            val fullReview = binding.etFullReview.text.toString().trim()
            val rating = binding.ratingBar.rating

            // Extract number from reading duration string if possible, else 0
            val readingTimeInt = readingDuration.filter { it.isDigit() }.toIntOrNull() ?: 0

            if (title.isNotEmpty() && fullReview.isNotEmpty()) {
                val newReview = ArticleReview(
                    title = title,
                    author = author.ifEmpty { "Anonymous" },
                    source = source.ifEmpty { "Personal Note" },
                    sourceUrl = binding.etImport.text.toString().trim(),
                    coverTag = "Philosophy", // Hardcode for now or get from chip group
                    dateRead = "Just now",
                    rating = rating,
                    readingTime = readingTimeInt,
                    shortSummary = shortSummary.ifEmpty { fullReview.take(50) + "..." },
                    fullReview = fullReview,
                    keyTakeaways = listOf("Personal reflection"),
                    recommendedFor = "Everyone",
                    reviewerMood = "Reflective" // Hardcode for now or get from chip group
                )

                Thread {
                    if (existingArticle != null) {
                        dbHelper.updateReview(newReview.copy(id = existingArticle.id))
                        runOnUiThread {
                            Toast.makeText(this, "Review updated!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    } else {
                        dbHelper.insertReview(newReview)
                        runOnUiThread {
                            Toast.makeText(this, "Review saved!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                }.start()
            } else {
                Toast.makeText(this, "Title and Full Review are required!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}