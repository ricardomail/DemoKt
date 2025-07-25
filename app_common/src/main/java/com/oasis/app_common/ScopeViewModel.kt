package com.oasis.app_common

import androidx.lifecycle.viewModelScope
import com.oasis.app_common.base.BaseResp
import com.oasis.app_common.base.BaseViewModel
import com.oasis.app_common.util.AppLogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean

open class ScopeViewModel : BaseViewModel() {
    // 原子标志位，确保协程作用域安全创建
    private val isCreatingScope = AtomicBoolean(false)

    private var _currentScope: CoroutineScope? = null
    protected val currentScope: CoroutineScope
        get() {
            synchronized(this) {
                return if (_currentScope?.isActive == true) {
                    _currentScope!!
                } else {
                    createNewScope().also {
                        _currentScope = it
                    }
                }
            }
        }


    private fun createNewScope(): CoroutineScope {
        if (!isCreatingScope.compareAndSet(false, true)) {
            throw IllegalStateException("作用域创建中")
        }
        return try {
            val supervisorJob = SupervisorJob()
            CoroutineScope(supervisorJob + Dispatchers.Main.immediate)
        } finally {
            isCreatingScope.set(false)
        }
    }


    protected fun <T> safeApiCallByOtherScope(
        onSuccess: suspend (T?) -> Unit = {},
        onError: suspend (Throwable) -> Unit = {
            AppLogUtil.i(it.message!!)
        },
        call: suspend () -> BaseResp<T>
    ) {
        currentScope.launch(Dispatchers.Main.immediate) {
            val result = call.invoke()
            when (result.responseState) {
                BaseResp.ResponseState.REQUEST_SUCCESS -> onSuccess(result.data)
                BaseResp.ResponseState.REQUEST_FAILED, BaseResp.ResponseState.REQUEST_ERROR -> onError(
                    IOException(result.errorMsg)
                )

                else -> {}
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        createNewScope().also {
            _currentScope?.cancel("ViewModel cleared")
            _currentScope = it
        }
    }
}