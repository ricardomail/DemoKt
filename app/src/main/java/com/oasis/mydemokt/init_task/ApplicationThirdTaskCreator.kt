package com.oasis.mydemokt.init_task

import com.xj.anchortask.library.AnchorTask
import com.xj.anchortask.library.IAnchorTaskCreator

class ApplicationThirdTaskCreator : IAnchorTaskCreator {
    override fun createTask(taskName: String): AnchorTask {
        return when (taskName) {
            ATConstants.KOIN_TASK -> {
                KoinTask()
            }

            ATConstants.ROUTER_TASK -> {
                RouterTask()
            }

            ATConstants.MMKV_TASK -> {
                MMKVTask()
            }

            ATConstants.TOAST_TASK -> {
                ToastTask()
            }

            ATConstants.LOG_TASK -> {
                LogTask()
            }

            ATConstants.COMMON_TASK -> {
                CommonTask()
            }

            else -> throw IllegalStateException("NO TASK")
        }
    }
}