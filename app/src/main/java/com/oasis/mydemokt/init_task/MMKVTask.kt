package com.oasis.mydemokt.init_task

import android.content.Context
import com.oasis.mydemokt.App
import com.tencent.mmkv.MMKV
import com.xj.anchortask.library.AnchorTask

class MMKVTask : AnchorTask(ATConstants.MMKV_TASK) {

    override fun run() {
        MMKV.initialize(App.instance)
    }
}