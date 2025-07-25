package com.oasis.app_me.viewmodel

import com.oasis.app_common.base.BaseViewModel
import com.oasis.app_me.bean.MyCollect
import com.oasis.app_me.repo.MeRepo
import com.oasis.app_common.base.RespStateLiveData
import com.oasis.app_common.base.RespStateMutableLiveData
import com.oasis.app_common.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MeViewModel(private val meRepo: MeRepo) : BaseViewModel() {
    private val _collectListState = MutableStateFlow<UiState<MyCollect>>(UiState.Loading)
    val collectListState: StateFlow<UiState<MyCollect>> = _collectListState.asStateFlow()

    fun getCollectList(currentPage: Int) = safeApiCall(onSuccess = {
        it?.run { _collectListState.emit(UiState.Success(this)) }
    }, onError = {_collectListState.emit(UiState.Error(it.message?:""))}) {
        meRepo.getCollectedByFlow(currentPage)
    }
}