package com.oasis.app_network.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.oasis.app_common.service.HelloService
import com.oasis.app_common.util.AppLogUtil

@Route(path = "/app_network/service/hello", name = "测试服务")
class HelloServiceImp : HelloService {
    override fun sayHello(name: String) {
        AppLogUtil.i("say hello execute")
    }

    override fun init(context: Context?) {
        AppLogUtil.i("hello service init")
    }
}