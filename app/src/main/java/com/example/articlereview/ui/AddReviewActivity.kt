package com.example.articlereview.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.articlereview.databinding.ActivityAddReviewBinding
import com.example.articlereview.model.ArticleReview
import com.example.articlereview.utils.StorageHelper

class AddReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddReviewBinding
    private lateinit var storageHelper: StorageHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storageHelper = StorageHelper(this)

        binding.btnSubmit.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val author = binding.etAuthor.text.toString().trim()
            val content = binding.etReviewContent.text.toString().trim()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                val newReview = ArticleReview(
                    id = System.currentTimeMillis().toInt(), // Bikin ID acak pakai waktu
                    title = title,
                    author = author.ifEmpty { "Anonim" },
                    source = "Personal Note",
                    sourceUrl = "", // Sesuai permintaan error
                    coverTag = "Uncategorized", // Ganti dari 'topic' ke 'coverTag'
                    dateRead = "Baru saja", // Ganti dari 'date' ke 'dateRead'
                    rating = 5.0f, // PERHATIKAN HURUF 'f'. Error karena sebelumnya 5.0 (Double), butuhnya Float.
                    readingTime = 1, // Ganti dari 'readTime' ke 'readingTime'
                    shortSummary = content.take(50) + "...", // Ganti dari 'tldr' ke 'shortSummary'
                    fullReview = content,
                    keyTakeaways = listOf("Catatan Pribadi"),
                    recommendedFor = "Semua orang",
                    reviewerMood = "Neutral" // Ganti dari 'mood' ke 'reviewerMood'
                )

                storageHelper.saveArticle(newReview)
                Toast.makeText(this, "Review disimpan!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Judul dan Isi Review wajib diisi!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}