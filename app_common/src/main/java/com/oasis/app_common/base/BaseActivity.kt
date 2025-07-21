package com.oasis.app_common.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.oasis.app_common.util.AppLogUtil
import com.oasis.app_common.util.LoadingViewUtil

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    protected val TAG = this.javaClass.simpleName
    private val loading = LoadingViewUtil()

    protected lateinit var mBind: T

    protected abstract fun getLayoutID(): Int

    abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBind = DataBindingUtil.setContentView(this, getLayoutID())

        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBind.unbind()
    }

    protected fun showLoadingDialog() {
        AppLogUtil.i(TAG)
        loading.showLoadingDialog(this, true)
    }

    protected fun dismissLoadingDialog() {
        loading.dismissLoadingDialog()
    }

}