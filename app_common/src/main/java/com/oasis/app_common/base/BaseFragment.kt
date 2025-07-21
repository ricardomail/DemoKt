package com.oasis.app_common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.oasis.app_common.util.LoadingViewUtil

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    protected val TAG = this.javaClass.simpleName
    protected lateinit var mBind: T
    protected lateinit var mContext: Context
    private val loading = LoadingViewUtil()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    abstract fun getLayoutID(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBind = DataBindingUtil.inflate(inflater, getLayoutID(), container, false)

        return mBind.root
    }

    protected fun showLoadingDialog() {
        loading.showLoadingDialog(mContext, true)
    }

    protected fun dismissLoadingDialog() {
        loading.dismissLoadingDialog()
    }
}