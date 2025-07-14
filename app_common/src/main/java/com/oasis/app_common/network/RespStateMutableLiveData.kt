package com.oasis.app_common.network

class RespStateMutableLiveData<T>: RespStateLiveData<T>() {
    public override fun postValue(value: BaseResp<T>) {
        super.postValue(value)
    }

    public override fun setValue(value: BaseResp<T>) {
        super.setValue(value)
    }
}