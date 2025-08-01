package com.sina.domain.model

import java.time.Instant

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
) {
    data class Article(
        val id: String,
        val author: String?,
        val content: String?,
        val description: String?,
        val publishedAt: Instant,
        val source: Source,
        val title: String,
        val url: String,
        val urlToImage: String?,
        val isBookmarked: Boolean = false
    )

    data class Source(
        val id: String?,
        val name: String
    )
}