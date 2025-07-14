package com.oasis.app_me.di

import com.oasis.app_me.api.MeApi
import com.oasis.app_me.repo.MeRepo
import com.oasis.app_me.viewmodel.MeViewModel
import com.oasis.app_network.okhttp.RetrofitManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {
    single { RetrofitManager.getService(MeApi::class.java) }
    single { MeRepo(get()) }
    viewModel { MeViewModel(get()) }

}