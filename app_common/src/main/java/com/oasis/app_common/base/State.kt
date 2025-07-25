package com.oasis.app_common.base

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()      // 加载中
    data class Success<T>(val data: T) : UiState<T>()  // 成功
    data class Error(val message: String) : UiState<Nothing>() // 失败
}