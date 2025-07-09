package com.oasis.app_common.base

import com.alibaba.android.arouter.launcher.ARouter
import com.oasis.app_common.network.BaseResp
import com.oasis.app_common.network.RespStateLiveData
import com.oasis.app_common.util.Constants
import com.oasis.app_common.util.ToastUtil
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

open class BaseRepository {
    suspend fun <T> dealResp(liveData: RespStateLiveData<T>, block: suspend () -> BaseResp<T>) {
        var result = BaseResp<T>()
        result.responseState = BaseResp.ResponseState.REQUEST_START
        liveData.postValue(result)
        try {
            result = block.invoke()
            when (result.errorCode) {
                Constants.HTTP_SUCCESS -> {
                    result.responseState = BaseResp.ResponseState.REQUEST_SUCCESS
                }

                Constants.HTTP_AUTH_INVALID -> {
                    result.responseState = BaseResp.ResponseState.REQUEST_FAILED
                    ARouter.getInstance().build(Constants.PATH_LOGIN).navigation()
                }

                else -> {
                    result.responseState = BaseResp.ResponseState.REQUEST_FAILED
                }
            }
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException,
                is HttpException,
                is ConnectException,
                -> {
                    //网络error
                    ToastUtil.showMsg("网络错误！")
                }

                else -> {
                    ToastUtil.showMsg("未知错误！")
                }
            }
            result.responseState = BaseResp.ResponseState.REQUEST_ERROR
        } finally {
            liveData.postValue(result)
        }
    }

}