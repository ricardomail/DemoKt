package com.oasis.app_network.okhttp

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer


class LogInterceptor : Interceptor {
    val TAG = "OkHttp"
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        // 打印请求信息
        val startTime = System.nanoTime()
        Log.d(TAG, String.format("--> %s %s", request.method, request.url))

        // 打印请求头
        request.headers.names().forEach { name ->
            Log.d("OkHttp", name + ": " + request.header(name));
        }

        request.body?.let {
            val buffer = Buffer()
            it.writeTo(buffer)
            Log.d(TAG, "Request Body: ${buffer.readUtf8()}")
        }
        val response = chain.proceed(request)

        // 打印响应信息
        val durationMs = (System.nanoTime() - startTime) / 1000000L
        Log.d("OkHttp","code : ${response.code} url : ${response.request.url} cost time: $durationMs")

        // 打印响应头
        response.headers.names().forEach { name ->
            Log.d("OkHttp", name + ": " + response.header(name))
        }


        // 打印响应体
        val responseBody = response.body
        responseBody?.let {
            val source = responseBody.source()
            source.request(Long.MAX_VALUE)
            val buffer = source.buffer
            Log.d("OkHttp", "Response Body: " + buffer.clone().readUtf8())
        }

        return response

    }
}