package com.oasis.mydemokt.init_task

import com.oasis.app_home.di.homeModule
import com.oasis.app_me.di.myModule
import com.oasis.app_navigation.di.naviModule
import com.oasis.app_project.di.projectModule
import com.oasis.app_user.di.userModule
import com.oasis.mydemokt.App
import com.oasis.mydemokt.appModule
import com.xj.anchortask.library.AnchorTask
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class KoinTask : AnchorTask(ATConstants.KOIN_TASK) {
    private val modules =
        mutableListOf(appModule, homeModule, userModule, myModule, projectModule, naviModule)

    override fun run() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(App.instance!!)
            modules(modules)
        }
    }
}