package com.oasis.app_user.viewmodel

import com.oasis.app_common.base.BaseViewModel
import com.oasis.app_common.base.RespStateLiveData
import com.oasis.app_common.base.RespStateMutableLiveData
import com.oasis.app_user.bean.LoginBean
import com.oasis.app_user.repo.LoginRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.io.IOException

class LoginViewModel(private val repo: LoginRepo) : BaseViewModel() {

    private val _data = RespStateMutableLiveData<LoginBean>()
    val data: RespStateLiveData<LoginBean> = _data

    private val _loginEventFlow = MutableSharedFlow<Result<String>>()
    val loginEventFlow: SharedFlow<Result<String>> = _loginEventFlow.asSharedFlow()

    private val _loginStateFlow = MutableSharedFlow<LoginBean>()
    val loginStateFlow: SharedFlow<LoginBean> = _loginStateFlow.asSharedFlow()

    fun login(username: String, password: String) {
        launch {
            repo.login(username, password, _data)
        }
    }

    fun loginByFlow(username: String, password: String) {
        safeApiCall(onSuccess = {
            _loginStateFlow.emit(it)
            _loginEventFlow.emit(Result.success("登录成功"))
        }, onError = {
            _loginEventFlow.emit(Result.failure(IOException(it.message ?: "登录失败")))
        }) {
            repo.login(username, password)
        }
    }
}