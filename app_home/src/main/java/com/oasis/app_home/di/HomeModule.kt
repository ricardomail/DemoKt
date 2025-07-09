package com.oasis.app_home.di

import com.oasis.app_common.network.RetrofitManager
import com.oasis.app_home.api.HomeApi
import com.oasis.app_home.repo.HomeRepo
import com.oasis.app_home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single { RetrofitManager.getService(HomeApi::class.java) }
    single { HomeRepo(get()) }
    viewModel { HomeViewModel(get()) }
}