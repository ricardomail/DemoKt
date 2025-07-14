package com.oasis.mydemokt.init_task

import com.oasis.app_home.di.homeModule
import com.oasis.app_me.di.myModule
import com.oasis.app_user.di.userModule
import com.oasis.mydemokt.App
import com.xj.anchortask.library.AnchorTask
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KoinTask : AnchorTask(ATConstants.KOIN_TASK) {
    private val modules = mutableListOf(homeModule, userModule, myModule)
    override fun run() {
        startKoin {
            androidLogger()
            androidContext(App.instance!!)
            modules(modules)
        }
    }
}