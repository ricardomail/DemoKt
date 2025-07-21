package com.oasis.app_user.repo

import com.oasis.app_common.base.BaseRepository
import com.oasis.app_common.base.BaseResp
import com.oasis.app_common.base.RespStateMutableLiveData
import com.oasis.app_user.api.UserApi
import com.oasis.app_user.bean.LoginBean

class LoginRepo(private val api: UserApi) : BaseRepository() {

    suspend fun login(
        username: String,
        password: String,
        data: RespStateMutableLiveData<LoginBean>
    ) =
        dealResp(data) {
            api.login(username, password)
        }

    suspend fun login(
        username: String,
        password: String
    ): BaseResp<LoginBean> {
        return dealResp {
            api.login(username, password)
        }
    }

}