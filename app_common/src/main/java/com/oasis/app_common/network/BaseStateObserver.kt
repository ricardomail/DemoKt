package com.oasis.app_common.network

import androidx.lifecycle.Observer

open class BaseStateObserver<T>(var t: Boolean? = null) : Observer<BaseResp<T>> {
    override fun onChanged(value: BaseResp<T>) {
        when (value.responseState) {
            BaseResp.ResponseState.REQUEST_START -> {
                getRespDataStart()
            }

            BaseResp.ResponseState.REQUEST_SUCCESS -> {
                if (value.data == null) {
                    getRespSuccess()
                } else {
                    getRespDataSuccess(value.data!!)
                }
            }

            BaseResp.ResponseState.REQUEST_FAILED -> {
                getRespDataEnd()
            }

            BaseResp.ResponseState.REQUEST_ERROR -> {
                getRespDataEnd()
            }

            else -> {}
        }

    }

    open fun getRespDataEnd() {

    }

    open fun getRespDataSuccess(data: T) {

    }

    open fun getRespSuccess() {

    }

    open fun getRespDataStart() {}
}