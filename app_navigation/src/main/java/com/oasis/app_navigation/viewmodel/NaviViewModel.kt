package com.oasis.app_navigation.viewmodel

import com.oasis.app_common.ScopeViewModel
import com.oasis.app_navigation.bean.Navi
import com.oasis.app_navigation.bean.Sys
import com.oasis.app_navigation.repo.NaviRepo
import com.oasis.app_common.base.RespStateLiveData
import com.oasis.app_common.base.RespStateMutableLiveData
import com.oasis.app_common.base.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NaviViewModel(private val repo: NaviRepo) : ScopeViewModel() {

    private var _naviList = RespStateMutableLiveData<List<Navi>>()
    var naviList: RespStateLiveData<List<Navi>> = _naviList

    private var _sysList = RespStateMutableLiveData<List<Sys>>()
    var sysList: RespStateLiveData<List<Sys>> = _sysList

    private var _naviListState = MutableStateFlow<UiState<List<Navi>>>(UiState.Loading)
    var naviListState: StateFlow<UiState<List<Navi>>> = _naviListState.asStateFlow()

    private var _sysListState = MutableStateFlow<UiState<List<Sys>>>(UiState.Loading)
    var sysListState: StateFlow<UiState<List<Sys>>> = _sysListState.asStateFlow()


//    fun getNavi() = currentScope.launch {
//        repo.getNavi(_naviList)
//    }

    fun getNavi() = safeApiCallByOtherScope(
        onSuccess = {
            it?.run { _naviListState.emit(UiState.Success(this)) }
        },
    ) {
        _naviListState.emit(UiState.Loading)
        repo.getNaviByFlow()
    }

    fun getSys() = safeApiCallByOtherScope(
        onSuccess = {
            it?.run { _sysListState.emit(UiState.Success(this)) }
        },
    ) {
        _naviListState.emit(UiState.Loading)
        repo.getSysByFlow()
    }

//    fun getSys() = currentScope.launch {
//        repo.getSys(_sysList)
//    }

}