package com.oasis.app_me.api

import com.oasis.app_me.bean.MyCollect
import com.oasis.app_network.base.BaseResp
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MeApi {

    @GET("lg/collect/list/{page}/json")
    suspend fun getCollectList(
        @Path("page") page: Int,
        @Query("page_size") page_size: Int
    ): BaseResp<MyCollect>
}