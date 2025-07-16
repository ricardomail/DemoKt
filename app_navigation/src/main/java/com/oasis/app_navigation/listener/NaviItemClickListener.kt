package com.oasis.app_navigation.listener

import com.oasis.app_navigation.bean.NaviItemEvent

fun interface NaviItemClickListener {
    fun onClick(e: NaviItemEvent)
}