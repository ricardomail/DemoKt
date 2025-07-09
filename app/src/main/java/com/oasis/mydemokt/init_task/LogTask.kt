package com.oasis.mydemokt.init_task

import android.content.Context
import com.oasis.app_common.util.AppLogUtil
import com.oasis.app_common.util.ToastUtil
import com.oasis.mydemokt.App
import com.tencent.mmkv.MMKV
import com.xj.anchortask.library.AnchorTask
import com.xj.anchortask.library.log.LogUtils

class LogTask : AnchorTask(ATConstants.LOG_TASK) {

    override fun run() {
        AppLogUtil.init(App.instance!!)
    }
}