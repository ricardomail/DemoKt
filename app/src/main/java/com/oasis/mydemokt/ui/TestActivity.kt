package com.oasis.mydemokt.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.oasis.app_common.base.BaseActivity
import com.oasis.mydemokt.R
import com.oasis.mydemokt.Test
import com.oasis.mydemokt.databinding.ActivityTestBinding
import com.xj.anchortask.library.log.LogUtils
import org.koin.android.ext.android.get
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.activityScope
import org.koin.androidx.scope.scope
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class TestActivity : BaseActivity<ActivityTestBinding>() {
    // 定义作用域ID，确保唯一性（通常使用activity的hashCode）
    private val scopeId: String by lazy { "TestActivityScope_${hashCode()}" }

    // 创建作用域实例
    private val activityScope: Scope by lazy {
        getKoin().createScope(
            scopeId = scopeId,
            qualifier = named<TestActivity>() // 与模块中定义的作用域类型匹配
        )
    }

        private val test: Test by lazy {
        activityScope.get()
    }
//    private val test: Test by inject<Test>()
    override fun getLayoutID(): Int {
        return R.layout.activity_test
    }

    override fun init() {
        LogUtils.d("test", "$test")
        mBind.button.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}