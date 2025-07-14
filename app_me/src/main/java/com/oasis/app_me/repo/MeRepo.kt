package com.oasis.app_me.repo

import com.oasis.app_me.api.MeApi
import com.oasis.app_me.bean.MyCollect
import com.oasis.app_network.base.BaseRepository
import com.oasis.app_network.okhttp.RespStateMutableLiveData

private const val PAGE_SIZE = 10

class MeRepo(val api: MeApi) : BaseRepository() {

    suspend fun getCollected(page: Int, data: RespStateMutableLiveData<MyCollect>) = dealResp(data) {
        api.getCollectList(page, PAGE_SIZE)
    }
}