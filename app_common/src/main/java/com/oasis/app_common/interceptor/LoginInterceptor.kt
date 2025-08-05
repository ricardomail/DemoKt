package com.oasis.app_common.interceptor

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.alibaba.android.arouter.launcher.ARouter
import com.oasis.app_common.util.AppLogUtil
import com.oasis.app_common.util.Constants
import com.oasis.app_common.util.KVUtil
import com.oasis.app_common.util.RouteExtras

@Interceptor(priority = 1, name = "LoginInterceptor") // 值越小优先级越高
class LoginInterceptor : IInterceptor {
    override fun init(context: Context?) {
        AppLogUtil.i("ARouter LoginInterceptor init")
    }

    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        val extra = postcard?.extra
        val needLogin =
            extra?.run { RouteExtras.hasFlag(postcard.extra, RouteExtras.NEED_LOGIN) } ?: false
        if (needLogin && KVUtil.getString(Constants.USER_NAME) == null) {
            callback?.onInterrupt(Exception("need login"))
            ARouter.getInstance().build(Constants.PATH_LOGIN).navigation()
        } else {
            callback?.onContinue(postcard)
        }
    }

}