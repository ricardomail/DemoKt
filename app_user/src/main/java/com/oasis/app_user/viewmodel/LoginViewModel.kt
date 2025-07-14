package com.oasis.app_user.viewmodel

import com.oasis.app_common.base.BaseViewModel
import com.oasis.app_common.network.RespStateLiveData
import com.oasis.app_user.bean.LoginBean
import com.oasis.app_user.repo.LoginRepo

class LoginViewModel(private val repo: LoginRepo) : BaseViewModel() {

    val data = RespStateLiveData<LoginBean>()

    fun login(username: String, password: String) {
        launch {
            repo.login(username, password, data)
        }
    }
}