package com.oasis.app_user.viewmodel

import com.oasis.app_common.base.BaseViewModel
import com.oasis.app_network.okhttp.RespStateLiveData
import com.oasis.app_network.okhttp.RespStateMutableLiveData
import com.oasis.app_user.bean.LoginBean
import com.oasis.app_user.repo.LoginRepo

class LoginViewModel(private val repo: LoginRepo) : BaseViewModel() {

    private val _data = RespStateMutableLiveData<LoginBean>()
    val data: RespStateLiveData<LoginBean> = _data

    fun login(username: String, password: String) {
        launch {
            repo.login(username, password, _data)
        }
    }
}