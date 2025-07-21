package com.oasis.app_navigation.repo

import com.oasis.app_navigation.api.NaviApi
import com.oasis.app_navigation.bean.Navi
import com.oasis.app_navigation.bean.Sys
import com.oasis.app_common.base.BaseRepository
import com.oasis.app_common.base.RespStateMutableLiveData

class NaviRepo(private val api: NaviApi) : BaseRepository() {

    suspend fun getSys(data: RespStateMutableLiveData<List<Sys>>) = dealResp(data) {
        api.getSys()
    }

    suspend fun getNavi(data: RespStateMutableLiveData<List<Navi>>) = dealResp(data) {
        api.getNavi()
    }
}