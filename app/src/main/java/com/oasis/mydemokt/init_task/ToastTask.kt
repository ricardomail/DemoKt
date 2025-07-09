package com.oasis.mydemokt.init_task

import android.content.Context
import com.oasis.app_common.util.ToastUtil
import com.oasis.mydemokt.App
import com.tencent.mmkv.MMKV
import com.xj.anchortask.library.AnchorTask

class ToastTask : AnchorTask(ATConstants.TOAST_TASK) {

    override fun run() {
        ToastUtil.init(App.instance!!)
    }
}