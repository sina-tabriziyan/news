package com.sina.news

import android.app.Application
import com.sina.core.di.coreModule
import com.sina.data.di.providers.dataModule
import com.sina.home.di.featuremodule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class NewsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApplication)
            modules(
                listOf(
                    coreModule,
                    dataModule,
                    featuremodule
                )
            )
        }
    }
}