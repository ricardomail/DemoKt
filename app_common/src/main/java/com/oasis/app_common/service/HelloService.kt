package com.oasis.app_common.service

import com.alibaba.android.arouter.facade.template.IProvider

interface HelloService : IProvider{
    fun sayHello(name: String)
}