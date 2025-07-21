package com.oasis.app_common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

typealias vmBLOCK = suspend () -> Unit



open class BaseViewModel : ViewModel() {
    // 统一管理Loading状态（多个请求时可扩展为Map）
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    protected fun launch(block: vmBLOCK) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            try {
                block.invoke()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    protected fun <T> safeApiCall(
        onSuccess: suspend (T) -> Unit,
        onError: suspend (Throwable) -> Unit,
        call: suspend () -> BaseResp<T>
    ) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            _loadingState.value = true
            val result = call.invoke()
            when (result.responseState) {
                BaseResp.ResponseState.REQUEST_SUCCESS -> onSuccess(result.data!!)
                BaseResp.ResponseState.REQUEST_FAILED, BaseResp.ResponseState.REQUEST_ERROR -> onError(
                    IOException(result.errorMsg)
                )

                else -> {}
            }
            _loadingState.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    private fun onError(e: Exception) {

    }
}