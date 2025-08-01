package com.sina.data.di.providers

import androidx.room.ProvidedTypeConverter
import androidx.room.Room
import androidx.room.TypeConverter
import com.sina.core.BuildConfig
import com.sina.data.local.database.NewsDatabase
import com.sina.data.local.datasource.INewsLocalDataSource
import com.sina.data.local.datasource.NewsLocalDataSource
import com.sina.data.remote.datasource.INewsRemoteDataSource
import com.sina.data.remote.datasource.NewsRemoteDataSource
import com.sina.data.repository.NewsRepository
import com.sina.domain.INewsRepository
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import java.time.Instant

val dataModule = module {
    single {
        Room.databaseBuilder(androidContext(), NewsDatabase::class.java, "newsDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<NewsDatabase>().articleDao() }
    single {
        provideKtorClient(BuildConfig.NEWS_API_KEY, CIO.create())
    }

    singleOf(::NewsLocalDataSource).bind<INewsLocalDataSource>()

    singleOf(::NewsRemoteDataSource).bind<INewsRemoteDataSource>()

    factoryOf(::NewsRepository).bind<INewsRepository>()
}


@ProvidedTypeConverter
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? = value?.let { Instant.ofEpochMilli(it) }

    @TypeConverter
    fun dateToTimestamp(instant: Instant?): Long? = instant?.toEpochMilli()
}