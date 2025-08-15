package com.oasis.app_common.hybird

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.oasis.app_common.util.Constants
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor

@Route(path = Constants.PATH_HYBIRD)
class HybirdActivity: FlutterActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        // 从 Intent 中获取路由参数
        val initialRoute = intent.getStringExtra("initial_route") ?: "/default_route"
        // 设置初始路由
        flutterEngine.navigationChannel.setInitialRoute(initialRoute)
    }

    override fun getInitialRoute(): String {
        return "/web"
    }


}