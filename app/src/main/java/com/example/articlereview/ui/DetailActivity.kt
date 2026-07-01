package com.example.articlereview.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.articlereview.databinding.ActivityDetailBinding
import com.example.articlereview.model.ArticleReview

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private var currentArticleId: Int = -1

    companion object {
        const val EXTRA_ARTICLE = "extra_article"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val article = intent.getSerializableExtra(EXTRA_ARTICLE) as? ArticleReview
        currentArticleId = article?.id ?: -1
        article?.let { bindArticle(it) }

        binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.fabEditReview.setOnClickListener {
            // Need the latest article object, which is currently bound.
            // But we can just use the initial or updated one via getSerializableExtra
            val currentArticle = intent.getSerializableExtra(EXTRA_ARTICLE) as? ArticleReview
            val editIntent = android.content.Intent(this, AddReviewActivity::class.java).apply {
                putExtra(EXTRA_ARTICLE, currentArticle)
            }
            startActivity(editIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        if (currentArticleId != -1) {
            Thread {
                val dbHelper = com.example.articlereview.model.DatabaseHelper(this)
                var updatedArticle = dbHelper.getReviewById(currentArticleId)
                
                // If not in DB, it might be a sample article
                if (updatedArticle == null) {
                    updatedArticle = com.example.articlereview.model.ArticleDataSource.getSampleArticles().find { it.id == currentArticleId }
                }

                runOnUiThread {
                    if (updatedArticle == null) {
                        // The article was deleted
                        finish()
                    } else {
                        bindArticle(updatedArticle)
                        // Update intent so edit button gets the latest
                        intent.putExtra(EXTRA_ARTICLE, updatedArticle)
                    }
                }
            }.start()
        }
    }

    private fun bindArticle(article: ArticleReview) {
        binding.apply {
            tvDetailTitle.text = article.title
            tvDetailAuthor.text = "by ${article.author}"
            tvDetailSource.text = article.source
            tvDetailTag.text = article.coverTag
            tvDetailDate.text = "Read on ${article.dateRead}"
            tvDetailReadingTime.text = "${article.readingTime} min read"
            tvDetailMood.text = article.reviewerMood
            ratingBarDetail.rating = article.rating
            tvDetailReview.text = article.fullReview

            // Takeaways
            val takeawaysText = article.keyTakeaways.mapIndexed { i, t ->
                "${i + 1}. $t"
            }.joinToString("\n\n")
            tvTakeaways.text = takeawaysText
        }
    }
}
