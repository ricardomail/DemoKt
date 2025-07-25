package com.oasis.app_common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oasis.app_common.util.AppLogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

typealias vmBLOCK = suspend () -> Unit


open class BaseViewModel : ViewModel() {

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
        onSuccess: suspend (T?) -> Unit = {},
        onError: suspend (Throwable) -> Unit = {
            AppLogUtil.i(it.message!!)
        },
        call: suspend () -> BaseResp<T>
    ) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
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
    }

    private fun onError(e: Exception) {

    }
}