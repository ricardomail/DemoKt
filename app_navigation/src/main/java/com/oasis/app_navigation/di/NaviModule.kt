package com.oasis.app_navigation.di

import com.oasis.app_navigation.api.NaviApi
import com.oasis.app_navigation.repo.NaviRepo
import com.oasis.app_navigation.viewmodel.NaviViewModel
import com.oasis.app_network.okhttp.RetrofitManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val naviModule = module {
    single { RetrofitManager.getService(NaviApi::class.java) }
    single { NaviRepo(get()) }
    viewModel { NaviViewModel(get()) }
}