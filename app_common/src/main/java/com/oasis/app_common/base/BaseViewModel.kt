package com.oasis.app_common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xj.anchortask.library.log.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

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

    override fun onCleared() {
        LogUtils.d("load", "onClear $this")
        super.onCleared()
    }

    private fun onError(e: Exception) {

    }
}