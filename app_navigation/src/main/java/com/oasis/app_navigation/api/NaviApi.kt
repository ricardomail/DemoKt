package com.oasis.app_navigation.api

import com.oasis.app_navigation.bean.Navi
import com.oasis.app_navigation.bean.Sys
import com.oasis.app_common.base.BaseResp
import retrofit2.http.GET

interface NaviApi {
    @GET("/navi/json")
    suspend fun getNavi(): BaseResp<List<Navi>>

    @GET("/tree/json")
    suspend fun getSys(): BaseResp<List<Sys>>
}