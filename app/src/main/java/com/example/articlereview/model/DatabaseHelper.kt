package com.example.articlereview.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "article_database.db"

        const val TABLE_REVIEWS = "reviews"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_SOURCE = "source"
        const val COLUMN_SOURCE_URL = "sourceUrl"
        const val COLUMN_COVER_TAG = "coverTag"
        const val COLUMN_READING_TIME = "readingTime"
        const val COLUMN_DATE_READ = "dateRead"
        const val COLUMN_RATING = "rating"
        const val COLUMN_SHORT_SUMMARY = "shortSummary"
        const val COLUMN_FULL_REVIEW = "fullReview"
        const val COLUMN_KEY_TAKEAWAYS = "keyTakeaways"
        const val COLUMN_RECOMMENDED_FOR = "recommendedFor"
        const val COLUMN_REVIEWER_MOOD = "reviewerMood"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_REVIEWS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_TITLE TEXT,"
                + "$COLUMN_AUTHOR TEXT,"
                + "$COLUMN_SOURCE TEXT,"
                + "$COLUMN_SOURCE_URL TEXT,"
                + "$COLUMN_COVER_TAG TEXT,"
                + "$COLUMN_READING_TIME INTEGER,"
                + "$COLUMN_DATE_READ TEXT,"
                + "$COLUMN_RATING REAL,"
                + "$COLUMN_SHORT_SUMMARY TEXT,"
                + "$COLUMN_FULL_REVIEW TEXT,"
                + "$COLUMN_KEY_TAKEAWAYS TEXT,"
                + "$COLUMN_RECOMMENDED_FOR TEXT,"
                + "$COLUMN_REVIEWER_MOOD TEXT)")
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_REVIEWS")
        onCreate(db)
    }

    fun insertReview(review: ArticleReview): Long {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COLUMN_TITLE, review.title)
        values.put(COLUMN_AUTHOR, review.author)
        values.put(COLUMN_SOURCE, review.source)
        values.put(COLUMN_SOURCE_URL, review.sourceUrl)
        values.put(COLUMN_COVER_TAG, review.coverTag)
        values.put(COLUMN_READING_TIME, review.readingTime)
        values.put(COLUMN_DATE_READ, review.dateRead)
        values.put(COLUMN_RATING, review.rating)
        values.put(COLUMN_SHORT_SUMMARY, review.shortSummary)
        values.put(COLUMN_FULL_REVIEW, review.fullReview)
        values.put(COLUMN_KEY_TAKEAWAYS, review.keyTakeaways.joinToString("|"))
        values.put(COLUMN_RECOMMENDED_FOR, review.recommendedFor)
        values.put(COLUMN_REVIEWER_MOOD, review.reviewerMood)

        val id = db.insert(TABLE_REVIEWS, null, values)
        return id
    }

    fun getAllReviews(): List<ArticleReview> {
        val reviewList = ArrayList<ArticleReview>()
        val selectQuery = "SELECT * FROM $TABLE_REVIEWS ORDER BY $COLUMN_ID DESC"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val review = ArticleReview(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR)),
                    source = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SOURCE)),
                    sourceUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SOURCE_URL)),
                    coverTag = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COVER_TAG)),
                    readingTime = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_READING_TIME)),
                    dateRead = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_READ)),
                    rating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_RATING)),
                    shortSummary = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SHORT_SUMMARY)),
                    fullReview = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULL_REVIEW)),
                    keyTakeaways = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_KEY_TAKEAWAYS)).split("|").filter { it.isNotEmpty() },
                    recommendedFor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECOMMENDED_FOR)),
                    reviewerMood = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEWER_MOOD))
                )
                reviewList.add(review)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return reviewList
    }

    fun getReviewById(id: Int): ArticleReview? {
        var review: ArticleReview? = null
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_REVIEWS WHERE $COLUMN_ID = ?", arrayOf(id.toString()))

        if (cursor.moveToFirst()) {
            review = ArticleReview(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR)),
                source = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SOURCE)),
                sourceUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SOURCE_URL)),
                coverTag = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COVER_TAG)),
                readingTime = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_READING_TIME)),
                dateRead = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_READ)),
                rating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_RATING)),
                shortSummary = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SHORT_SUMMARY)),
                fullReview = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULL_REVIEW)),
                keyTakeaways = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_KEY_TAKEAWAYS)).split("|").filter { it.isNotEmpty() },
                recommendedFor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECOMMENDED_FOR)),
                reviewerMood = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEWER_MOOD))
            )
        }
        cursor.close()
        return review
    }

    fun updateReview(review: ArticleReview): Int {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COLUMN_TITLE, review.title)
        values.put(COLUMN_AUTHOR, review.author)
        values.put(COLUMN_SOURCE, review.source)
        values.put(COLUMN_SOURCE_URL, review.sourceUrl)
        values.put(COLUMN_COVER_TAG, review.coverTag)
        values.put(COLUMN_READING_TIME, review.readingTime)
        values.put(COLUMN_DATE_READ, review.dateRead)
        values.put(COLUMN_RATING, review.rating)
        values.put(COLUMN_SHORT_SUMMARY, review.shortSummary)
        values.put(COLUMN_FULL_REVIEW, review.fullReview)
        values.put(COLUMN_KEY_TAKEAWAYS, review.keyTakeaways.joinToString("|"))
        values.put(COLUMN_RECOMMENDED_FOR, review.recommendedFor)
        values.put(COLUMN_REVIEWER_MOOD, review.reviewerMood)

        val rowsUpdated = db.update(TABLE_REVIEWS, values, "$COLUMN_ID = ?", arrayOf(review.id.toString()))
        return rowsUpdated
    }

    fun deleteReview(id: Int): Int {
        val db = this.writableDatabase
        val rowsDeleted = db.delete(TABLE_REVIEWS, "$COLUMN_ID = ?", arrayOf(id.toString()))
        return rowsDeleted
    }
}
