package com.oasis.app_common.util

import android.view.View
import com.oasis.app_common.R

private const val DEBOUNCE_DELAY = 600L // 默认600ms防抖时间

fun View.setSingleClickListener(
    delay: Long = DEBOUNCE_DELAY,
    onClick: (View) -> Unit
) {
    setOnClickListener {
        val lastClickTime = getTag(R.id.last_click_time_key) as Long? ?: 0L

        val now = System.currentTimeMillis()
        if (now - lastClickTime >= delay) {
            setTag(R.id.last_click_time_key, now)
            onClick(it)
        }
    }
}