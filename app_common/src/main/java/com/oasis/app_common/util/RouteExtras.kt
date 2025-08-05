package com.oasis.app_common.util

object RouteExtras {
    // 需要登录：第1位（从0开始）
    const val NEED_LOGIN: Int = 1 // 二进制：00000001

    // 需要实名认证：第2位
    const val NEED_REAL_NAME: Int = 1 shl 1 // 二进制：00000010

    // 需要绑卡：第3位
    const val NEED_BIND_CARD: Int = 1 shl 2 // 二进制：00000100

    // 可以组合多个标记，例如：需要登录且需要实名认证
    const val LOGIN_AND_REAL_NAME: Int = NEED_LOGIN or NEED_REAL_NAME // 二进制：00000011

    // 检查开关是否打开
    fun hasFlag(extras: Int, flag: Int): Boolean {
        return (extras and flag) == flag
    }

    // 设置开关
    fun setFlag(base: Int, flag: Int): Int {
        return base or flag
    }
}