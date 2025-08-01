package com.sina.data.local.datasource

import com.sina.data.local.entities.ArticleEntity
import com.sina.library.response.DataError
import com.sina.library.response.Result

interface INewsLocalDataSource {
    suspend fun searchArticles(query: String): Result<List<ArticleEntity>, DataError.Local>
    suspend fun insertOrUpdate(article: ArticleEntity)

    suspend fun delete(article: ArticleEntity)

    suspend fun getAllArticles(): Result<List<ArticleEntity>, DataError.Local>

    suspend fun getArticleById(id: String): Result<ArticleEntity?, DataError.Local>

}