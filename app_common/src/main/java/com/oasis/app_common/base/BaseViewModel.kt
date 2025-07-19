package com.oasis.app_common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        super.onCleared()
    }

    private fun onError(e: Exception) {

    }
}