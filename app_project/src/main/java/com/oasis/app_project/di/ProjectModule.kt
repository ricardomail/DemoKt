package com.oasis.app_project.di

import com.oasis.app_common.di.ScopeOwner
import com.oasis.app_network.okhttp.RetrofitManager
import com.oasis.app_project.api.ProjectApi
import com.oasis.app_project.repo.ProjectRepo
import com.oasis.app_project.ui.ProjectFragment
import com.oasis.app_project.viewmodel.ProjectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val projectModule = module {
    single { RetrofitManager.getService(ProjectApi::class.java) }
    single { ProjectRepo(get()) }
    viewModel { ProjectViewModel(get()) }
//    scope<ScopeOwner>{
//        scoped { (fragmentId: String) ->
//            // 按 fragment ID 创建独立的 ViewModel
//            ProjectViewModel(get())
//        }
//    }

    scope<ProjectFragment> {
        viewModel { ProjectViewModel(get()) }
    }
}