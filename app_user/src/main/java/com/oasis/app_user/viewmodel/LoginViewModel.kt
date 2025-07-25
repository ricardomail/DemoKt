package com.oasis.app_user.viewmodel

import com.oasis.app_common.base.BaseViewModel
import com.oasis.app_common.base.RespStateLiveData
import com.oasis.app_common.base.RespStateMutableLiveData
import com.oasis.app_common.base.UiState
import com.oasis.app_user.bean.LoginBean
import com.oasis.app_user.repo.LoginRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.io.IOException

class LoginViewModel(private val repo: LoginRepo) : BaseViewModel() {

    private val _data = RespStateMutableLiveData<LoginBean>()
    val data: RespStateLiveData<LoginBean> = _data

    private val _loginEventFlow = MutableSharedFlow<UiState<LoginBean>>()
    val loginEventFlow: SharedFlow<UiState<LoginBean>> = _loginEventFlow.asSharedFlow()
    fun login(username: String, password: String) {
        launch {
            repo.login(username, password, _data)
        }
    }

    fun loginByFlow(username: String, password: String) {
        safeApiCall(onSuccess = {
            it?.run { _loginEventFlow.emit(UiState.Success(this, message = "登录成功")) }
        }, onError = {
            _loginEventFlow.emit(UiState.Error(it.message ?: "登录失败"))
        }) {
            _loginEventFlow.emit(UiState.Loading)
            repo.login(username, password)
        }
    }
}