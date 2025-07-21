package com.oasis.app_common.base

import androidx.lifecycle.LiveData

open class RespStateLiveData<T>: LiveData<BaseResp<T>>()