package com.sina.core.di

import android.content.Context
import android.net.ConnectivityManager
import com.sina.library.network.ConnectivityTools

fun provideConnectivityManager(context: Context): ConnectivityManager {
    return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}

 fun provideNetworkUtils(context: Context): ConnectivityTools {
    return ConnectivityTools(context)
}