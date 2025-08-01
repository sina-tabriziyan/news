package com.sina.data.di.providers

import com.sina.core.BuildConfig

fun constructUrl(url: String): String = when {
    url.contains(BuildConfig.BASE_URL) -> url
    url.startsWith("/") -> BuildConfig.BASE_URL + url.drop(1)
    else -> BuildConfig.BASE_URL + url
}