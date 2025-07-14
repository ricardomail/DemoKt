package com.oasis.app_user.repo

import com.oasis.app_common.base.BaseRepository
import com.oasis.app_common.network.RespStateLiveData
import com.oasis.app_user.api.UserApi
import com.oasis.app_user.bean.LoginBean

class LoginRepo(private val api: UserApi) : BaseRepository() {

    suspend fun login(username: String, password: String, data: RespStateLiveData<LoginBean>) =
        dealResp(data) {
            api.login(username, password)
        }

}