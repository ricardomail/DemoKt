package com.oasis.app_common.service

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter

class Test {
    init {
        ARouter.getInstance().inject(this)
    }
    // kotlin中需要增加@JvmField注解
    @Autowired
    @JvmField
    var helloService: HelloService? = null
//
//    @Autowired(name = "/app_network/service/hello")
//    var helloService2: HelloService? = null

    var helloService3: HelloService? = null

    var helloService4: HelloService? = null


    fun testService(){
//        helloService3 = ARouter.getInstance().build("/app_network/service/hello").navigation() as HelloService?
//        helloService3?.sayHello("123")

        helloService4 = ARouter.getInstance().navigation(HelloService::class.java)
//        helloService4?.sayHello("123")
//
//        helloService?.sayHello("123")
    }


}