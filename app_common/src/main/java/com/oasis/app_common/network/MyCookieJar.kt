package com.oasis.app_common.network

import android.util.Log
import com.google.gson.Gson
import com.oasis.app_common.util.Constants
import com.oasis.app_common.util.KVUtil
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class MyCookieJar : CookieJar {
    private val gson by lazy { Gson() }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val s = KVUtil.getString(Constants.USER_COOKIE)
        if (s != null) {
            val l = gson.fromJson(s, CookieBean::class.java).list
            return l
        }
        return arrayListOf()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        if (KVUtil.getString(Constants.USER_COOKIE) != null) {
            return
        }

        for (item in cookies) {
            if (item.toString().contains("loginUserName")) {
                Log.d("OkHttp", "saveFromResponse:3")
                KVUtil.put(Constants.USER_COOKIE, gson.toJson(CookieBean(list = cookies)))
                break
            }
        }
    }
}