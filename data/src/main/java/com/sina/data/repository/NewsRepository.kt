package com.sina.data.repository

import com.sina.data.local.datasource.INewsLocalDataSource
import com.sina.data.local.entities.toDomain
import com.sina.data.local.entities.toEntity
import com.sina.data.remote.datasource.INewsRemoteDataSource
import com.sina.domain.INewsRepository
import com.sina.domain.model.News
import com.sina.library.network.ConnectivityTools
import com.sina.library.response.DataError
import com.sina.library.response.Result
import com.sina.library.response.asResultBody

class NewsRepository(
    private val remote: INewsRemoteDataSource,
    private val local: INewsLocalDataSource,
    private val connectivityTools: ConnectivityTools
) : INewsRepository {
    override suspend fun getYesterdayNews(search: String): Result<News, DataError.Local> {
        return if (connectivityTools.isNetworkAvailable()) {
            when (val remoteResult = remote.getNewsYesterday().asResultBody()) {
                is Result.Error -> getCachedArticlesOrError(remoteResult.error, search)

                is Result.Success -> {
                    remoteResult.data.toDomain().articles.forEach { article ->
                        local.insertOrUpdate(article.toEntity())
                    }
                    getCachedArticlesOrError(null, search)
                }
            }
        } else getCachedArticlesOrError(DataError.Network.NO_INTERNET, search)
    }

    private suspend fun getCachedArticlesOrError(
        networkError: DataError.Network?,
        search: String
    ): Result<News, DataError.Local> {
        val localResult = if (search.isBlank()) local.getAllArticles()
        else local.searchArticles(search)
        return when (localResult) {
            is Result.Success -> {
                val articles = localResult.data.map { it.toDomain() }
                Result.Success(
                    News(
                        articles = articles,
                        status = if (networkError != null) "cached" else "online",
                        totalResults = articles.size
                    )
                )
            }

            is Result.Error -> Result.Error(localResult.error)
        }
    }

    override suspend fun getArticle(articleId: String): Result<News.Article, DataError.Local> {
        return when (val result = local.getArticleById(articleId)) {
            is Result.Success -> {
                result.data?.let {
                    Result.Success(it.toDomain())
                } ?: run {
                    Result.Error(DataError.Local.FILE_NOT_FOUND)
                }
            }

            is Result.Error -> Result.Error(result.error)
        }
    }
}