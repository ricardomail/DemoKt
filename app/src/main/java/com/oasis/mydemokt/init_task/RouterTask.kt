package com.oasis.mydemokt.init_task

import com.alibaba.android.arouter.launcher.ARouter
import com.oasis.mydemokt.App
import com.xj.anchortask.library.AnchorTask

class RouterTask : AnchorTask(ATConstants.ROUTER_TASK) {
    override fun run() {
//        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
//        }
        ARouter.init(App.instance)
    }

    override fun isRunOnMainThread(): Boolean {
        return false
    }

}