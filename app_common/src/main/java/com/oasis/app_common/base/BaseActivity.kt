package com.oasis.app_common.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.oasis.app_common.util.AppLogUtil
import com.oasis.app_common.util.LoadingViewUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    fun launchByRepeat(
        dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
        state: Lifecycle.State = Lifecycle.State.STARTED,
        block: suspend () -> Unit
    ) {
        lifecycleScope.launch(dispatcher) {
            launch {
                repeatOnLifecycle(state) {
                    block.invoke()
                }
            }
        }
    }

    fun <T> handleUiState(
        state: UiState<T>, needHandleLoading: Boolean = false, onLoading: () -> Unit = {

        }, onSuccess: (T, String) -> Unit = { _, _ ->

        }, onError: (String) -> Unit = {
        }, onFinish: () -> Unit = { if (needHandleLoading) dismissLoadingDialog() }
    ) {
        when (state) {
            is UiState.Loading -> {
                if (needHandleLoading) showLoadingDialog()
                onLoading.invoke()
            }

            is UiState.Success -> {
                dismissLoadingDialog()
                onSuccess.invoke(state.data, state.message)
            }

            is UiState.Error -> {
                onError.invoke(state.message)
            }
        }
        onFinish.invoke()
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