package com.sina.core.di

import org.koin.dsl.module

val coreModule = module {
    single { provideConnectivityManager(get()) }

    single { provideNetworkUtils(get()) }
}