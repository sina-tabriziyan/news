package com.sina.home.di

import com.sina.home.home.HomeVM
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val featuremodule=module{
    viewModelOf(::HomeVM)
}