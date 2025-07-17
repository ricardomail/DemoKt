package com.oasis.mydemokt

import com.oasis.mydemokt.ui.TestActivity
import org.koin.dsl.module

val appModule = module {
//    scope<TestActivity> {
//        scoped { Test() }
//    }

    scope<TestActivity> {
        scoped {
            Test()
        }
    }
//    single {
//        Test()
//    }
}