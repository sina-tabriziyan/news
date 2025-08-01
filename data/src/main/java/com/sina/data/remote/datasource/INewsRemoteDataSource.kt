package com.sina.data.remote.datasource

import com.sina.data.remote.dtos.yesterday.NewsDto
import com.sina.library.response.ApiSuccess
import com.sina.library.response.DataError
import com.sina.library.response.Result

interface INewsRemoteDataSource {
    suspend fun getNewsYesterday(): Result<ApiSuccess<NewsDto>, DataError.Network>
}