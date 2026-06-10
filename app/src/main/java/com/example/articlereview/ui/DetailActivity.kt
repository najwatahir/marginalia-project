package com.example.articlereview.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.articlereview.databinding.ActivityDetailBinding
import com.example.articlereview.model.ArticleReview

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_ARTICLE = "extra_article"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val article = intent.getSerializableExtra(EXTRA_ARTICLE) as? ArticleReview
        article?.let { bindArticle(it) }

        binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
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
            tvDetailRatingNum.text = String.format("%.1f / 5.0", article.rating)
            tvDetailSummary.text = article.shortSummary
            tvDetailReview.text = article.fullReview
            tvRecommendedFor.text = article.recommendedFor

            // Takeaways
            val takeawaysText = article.keyTakeaways.mapIndexed { i, t ->
                "${i + 1}. $t"
            }.joinToString("\n\n")
            tvTakeaways.text = takeawaysText

            // Tag color
            val tagColor = getTagColor(article.coverTag)
            tvDetailTag.setTextColor(tagColor)
            dividerTagAccent.setBackgroundColor(tagColor)
        }
    }

    private fun getTagColor(tag: String): Int {
        return when (tag) {
            "Philosophy" -> getColor(com.example.articlereview.R.color.tag_philosophy)
            "Psychology" -> getColor(com.example.articlereview.R.color.tag_psychology)
            "Wellbeing" -> getColor(com.example.articlereview.R.color.tag_wellbeing)
            "Economics" -> getColor(com.example.articlereview.R.color.tag_economics)
            "Science" -> getColor(com.example.articlereview.R.color.tag_science)
            "Lifestyle" -> getColor(com.example.articlereview.R.color.tag_lifestyle)
            else -> getColor(com.example.articlereview.R.color.accent_green)
        }
    }
}
