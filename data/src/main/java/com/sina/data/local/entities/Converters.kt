package com.sina.data.local.entities

import com.sina.data.remote.dtos.yesterday.NewsDto
import com.sina.domain.model.News
import java.time.Instant

fun NewsDto.toDomain(): News {
    return News(
        articles = articles.map { it.toDomain() },
        status = status,
        totalResults = totalResults
    )
}

fun NewsDto.Article.toDomain(): News.Article {
    return News.Article(
        id = generateArticleId(url, publishedAt),
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt.toInstant(),
        source = News.Source(source.id, source.name),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun News.Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt.toEpochMilli(),
        sourceId = source.id,
        sourceName = source.name,
        title = title,
        url = url,
        urlToImage = urlToImage,
        isBookmarked = isBookmarked
    )
}

fun ArticleEntity.toDomain(): News.Article {
    return News.Article(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = Instant.ofEpochMilli(publishedAt),
        source = News.Source(sourceId, sourceName),
        title = title,
        url = url,
        urlToImage = urlToImage,
        isBookmarked = isBookmarked
    )
}

private fun generateArticleId(url: String, publishedAt: String): String {
    return (url + publishedAt).hashCode().toString()
}

private fun String.toInstant(): Instant {
    return try {
        Instant.parse(this)
    } catch (e: Exception) {
        Instant.now()
    }
}