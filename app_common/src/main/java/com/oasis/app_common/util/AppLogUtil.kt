package com.oasis.app_common.util

import android.content.Context
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.elvishew.xlog.interceptor.BlacklistTagsFilterInterceptor
import com.elvishew.xlog.printer.AndroidPrinter
import com.elvishew.xlog.printer.ConsolePrinter
import com.elvishew.xlog.printer.Printer
import com.elvishew.xlog.printer.file.FilePrinter


/**
 * Created by stew on 2023/5/16.
 * mail: stewforani@gmail.com
 */
object AppLogUtil {
    fun init(context: Context) {
        val config = LogConfiguration.Builder()
            .logLevel(LogLevel.ALL)
            .tag("APP_TAG")
            .apply {
                // 可以添加判断 比如debug模式下打印
//                enableThreadInfo()
//                enableStackTrace(2)
            }.enableBorder()
            .addInterceptor(BlacklistTagsFilterInterceptor("")) // 添加黑名单
            .build()
        val androidPrinter = AndroidPrinter(true)
        val consolePrinter: Printer = ConsolePrinter()
        val filePrinter =
            FilePrinter.Builder(context.getExternalFilesDir(null)?.absolutePath + "/logs").build()
        XLog.init(config, androidPrinter, consolePrinter, filePrinter)

    }

    fun d(message: String) {
        XLog.d(message)
    }

    fun <T> d(collection: Array<T>) {
        XLog.d(collection)
    }

    fun d(map: Map<Any, Any>) {
        XLog.d(map)
    }

    fun i(message: String) {
        XLog.i(message)
    }

    fun <T> i(collection: Array<T>) {
        XLog.i(collection)
    }

    fun i(map: Map<Any, Any>) {
        XLog.i(map)
    }

    fun e(message: String, throws: Throws) {
        XLog.e(message, throws)
    }

    fun json(message: String) {
        XLog.json(message)
    }

    fun xml(message: String) {
        XLog.xml(message)
    }

}