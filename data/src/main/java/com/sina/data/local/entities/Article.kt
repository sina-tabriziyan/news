package com.sina.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey
    val id: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: Long,
    val sourceId: String?,
    val sourceName: String,
    val title: String,
    val url: String,
    val urlToImage: String?,
    val isBookmarked: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis()
)