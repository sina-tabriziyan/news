package com.sina.data.remote.datasource

import com.sina.data.di.providers.constructUrl
import com.sina.data.remote.dtos.yesterday.NewsDto
import com.sina.library.response.ApiSuccess
import com.sina.library.response.DataError
import com.sina.library.response.Result
import com.sina.library.response.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewsRemoteDataSource(
    private val client: HttpClient
) : INewsRemoteDataSource {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    override suspend fun getNewsYesterday(): Result<ApiSuccess<NewsDto>, DataError.Network> {
        val today = LocalDate.now()
        val yesterday = today.minusDays(1)

        return safeApiCall(apiCall = {
            client.get(constructUrl("everything")) {
                parameter("q", "Microsoft OR Apple OR Google OR Tesla")
                parameter("from", dateFormatter.format(yesterday))
                parameter("to", dateFormatter.format(today))
                parameter("sortBy", "publishedAt")
            }
        })
    }
}
