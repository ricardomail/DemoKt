package com.oasis.app_common.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.oasis.app_common.util.ToastUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseVMFragment<T : ViewDataBinding> : BaseFragment<T>() {

    protected var currentPage = 0
    protected var currentPageSize = 10
    private var isFirstLoad = true

    abstract fun observe()

    abstract fun init()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        init()
    }

    override fun onResume() {
        super.onResume()
        if (isFirstLoad) {
            isFirstLoad = false
            lazyLoad()
        }
    }

    fun launchByRepeat(
        dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
        state: Lifecycle.State = Lifecycle.State.STARTED,
        block: suspend () -> Unit
    ) {
        lifecycleScope.launch(dispatcher) {
            repeatOnLifecycle(state) {
                block.invoke()
            }
        }
    }

    fun <T> handleUiState(
        state: UiState<T>, needHandleLoading: Boolean = false, onLoading: () -> Unit = {

        }, onSuccess: (T) -> Unit = {

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
                onSuccess.invoke(state.data)
            }

            is UiState.Error -> {
                onError.invoke(state.message)
            }
        }
        onFinish.invoke()
    }

    open fun lazyLoad() {

    }

    open fun resetState() {}
}