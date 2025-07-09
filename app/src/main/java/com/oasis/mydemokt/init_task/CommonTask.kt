package com.oasis.mydemokt.init_task

import android.content.Context
import com.oasis.app_common.util.AppCommonUitl
import com.oasis.app_common.util.ToastUtil
import com.oasis.mydemokt.App
import com.tencent.mmkv.MMKV
import com.xj.anchortask.library.AnchorTask

class CommonTask : AnchorTask(ATConstants.COMMON_TASK) {

    override fun run() {
        AppCommonUitl.init(App.instance!!)
    }
}