package com.sina.domain

import com.sina.domain.model.News
import com.sina.library.response.DataError
import com.sina.library.response.Result

interface INewsRepository {
    suspend fun getYesterdayNews(search: String = ""): Result<News, DataError.Local>
    suspend fun getArticle(article:String) :Result<News.Article, DataError.Local>
}