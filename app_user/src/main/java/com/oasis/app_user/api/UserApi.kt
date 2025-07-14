package com.oasis.app_user.api

import com.oasis.app_network.base.BaseResp
import com.oasis.app_user.bean.LoginBean
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserApi {

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): BaseResp<LoginBean>
}