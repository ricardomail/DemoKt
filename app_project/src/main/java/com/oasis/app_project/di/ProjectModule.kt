package com.oasis.app_project.di

import com.oasis.app_network.okhttp.RetrofitManager
import com.oasis.app_project.api.ProjectApi
import com.oasis.app_project.repo.ProjectRepo
import com.oasis.app_project.viewmodel.ProjectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val projectModule = module {
    single { RetrofitManager.getService(ProjectApi::class.java) }
    single { ProjectRepo(get()) }
    viewModel { ProjectViewModel(get()) }
}