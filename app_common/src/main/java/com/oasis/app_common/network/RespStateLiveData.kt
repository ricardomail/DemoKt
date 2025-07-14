package com.oasis.app_common.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class RespStateLiveData<T>: LiveData<BaseResp<T>>()