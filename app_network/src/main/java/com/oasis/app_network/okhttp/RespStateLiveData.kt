package com.oasis.app_network.okhttp

import androidx.lifecycle.LiveData
import com.oasis.app_network.base.BaseResp

open class RespStateLiveData<T>: LiveData<BaseResp<T>>()