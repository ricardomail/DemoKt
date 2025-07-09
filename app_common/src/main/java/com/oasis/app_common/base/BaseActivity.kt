package com.oasis.app_common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.oasis.app_common.util.LoadingViewUtil

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    protected val TAG = this.javaClass.simpleName

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
        LoadingViewUtil.showLoadingDialog(this, true)
    }

    protected fun dismissLoadingDialog() {
        LoadingViewUtil.dismissLoadingDialog()
    }

}