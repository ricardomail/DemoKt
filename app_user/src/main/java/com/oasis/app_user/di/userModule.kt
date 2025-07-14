package com.oasis.app_user.di

import com.oasis.app_common.network.RetrofitManager
import com.oasis.app_user.api.UserApi
import com.oasis.app_user.repo.LoginRepo
import com.oasis.app_user.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userModule = module {
    single { RetrofitManager.getService(UserApi::class.java) }
    single { LoginRepo(get()) }
    viewModel { LoginViewModel(get()) }
}