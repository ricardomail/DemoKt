package com.oasis.mydemokt.init_task

import android.content.Context
import com.oasis.app_home.di.homeModule
import com.oasis.mydemokt.App
import com.xj.anchortask.library.AnchorTask
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class KoinTask: AnchorTask(ATConstants.KOIN_TASK) {
    private val modules = mutableListOf(homeModule)
    override fun run() {
        startKoin {
            androidLogger()
            androidContext(App.instance!!)
            modules(modules)
        }
    }
}