package com.sina.data.remote.dtos.yesterday

import com.sina.data.local.entities.ArticleEntity
import com.sina.domain.model.News
import kotlinx.serialization.Serializable
import java.time.Instant
import kotlin.collections.map

@Serializable
data class NewsDto(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
) {
    @Serializable
    data class Article(
        val author: String? = null,
        val content: String? = null,
        val description: String? = null,
        val publishedAt: String,
        val source: Source,
        val title: String,
        val url: String,
        val urlToImage: String? = null
    )


    @Serializable
    data class Source(
        val id: String?,
        val name: String
    )
}

