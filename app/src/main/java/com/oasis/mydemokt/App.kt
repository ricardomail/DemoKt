package com.oasis.mydemokt

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.oasis.app_common.util.AppCommonUitl
import com.oasis.app_common.util.Constants
import com.oasis.app_common.util.KVUtil
import com.oasis.mydemokt.init_task.ATConstants
import com.oasis.mydemokt.init_task.ApplicationThirdTaskCreator
import com.oasis.mydemokt.lifecycle.LifecycleObj
import com.xj.anchortask.library.AnchorProject
import com.xj.anchortask.library.log.LogUtils

class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initAsyncThirdParty()
        initUserDarkMode()
        registerActivityLifecycle()
    }


    private fun initUserDarkMode() {
        when (val userDarkMode = KVUtil.getInt(Constants.USER_DARK_MODE, 999)) {
            999 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            1000 -> AppCommonUitl.setFollowSystemTheme()
            else -> AppCompatDelegate.setDefaultNightMode(userDarkMode)
        }
    }

    private fun initAsyncThirdParty() {
        AnchorProject.Builder().setContext(this).setLogLevel(LogUtils.LogLevel.DEBUG)
            .setAnchorTaskCreator(ApplicationThirdTaskCreator())
            .addTask(ATConstants.LOG_TASK)
            .addTask(ATConstants.COMMON_TASK)
            .addTask(ATConstants.TOAST_TASK).afterTask(ATConstants.LOG_TASK)
            .addTask(ATConstants.MMKV_TASK)
            .addTask(ATConstants.ROUTER_TASK)
            .addTask(ATConstants.KOIN_TASK).build().also {
                it.start().await()
            }
    }

    private fun registerActivityLifecycle() {
        registerActivityLifecycleCallbacks(LifecycleObj.instance)
    }

    companion object {
        var instance: App? = null
    }


}