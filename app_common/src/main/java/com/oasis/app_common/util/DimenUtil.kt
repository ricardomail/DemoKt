package com.oasis.app_common.util

import android.content.res.Resources

fun Double.dpToPx() : Int{
    val density: Float = Resources.getSystem().displayMetrics.density
    val value = Math.round(this * density).toInt()
    return value
}

fun Double.pxToDp() : Int{
    val density: Float = Resources.getSystem().displayMetrics.density
    val value = Math.round(this / density).toInt()
    return value
}