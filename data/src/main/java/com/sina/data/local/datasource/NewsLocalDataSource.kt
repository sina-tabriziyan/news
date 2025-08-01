package com.sina.data.local.datasource

import com.sina.data.local.database.ArticleDao
import com.sina.data.local.entities.ArticleEntity
import com.sina.library.response.DataError
import com.sina.library.response.Result
import com.sina.library.response.safeLocalCall

class NewsLocalDataSource(private val articleDao: ArticleDao) : INewsLocalDataSource {
    override suspend fun insertOrUpdate(article: ArticleEntity) = articleDao.insertOrUpdate(article)

    override suspend fun delete(article: ArticleEntity) = articleDao.delete(article)

    override suspend fun getAllArticles(): Result<List<ArticleEntity>, DataError.Local> =
        safeLocalCall { articleDao.getAllArticles() }

    override suspend fun getArticleById(id: String): Result<ArticleEntity?, DataError.Local> =
        safeLocalCall { articleDao.getArticleById(id) }

    override suspend fun searchArticles(query: String): Result<List<ArticleEntity>, DataError.Local> =
        safeLocalCall { articleDao.searchArticles(query) }
}

