package com.oasis.app_navigation.viewmodel

import com.oasis.app_common.ScopeViewModel
import com.oasis.app_navigation.bean.Navi
import com.oasis.app_navigation.bean.Sys
import com.oasis.app_navigation.repo.NaviRepo
import com.oasis.app_common.base.RespStateLiveData
import com.oasis.app_common.base.RespStateMutableLiveData
import kotlinx.coroutines.launch

class NaviViewModel(private val repo: NaviRepo) : ScopeViewModel() {

    private var _naviList = RespStateMutableLiveData<List<Navi>>()
    var naviList: RespStateLiveData<List<Navi>> = _naviList

    private var _sysList = RespStateMutableLiveData<List<Sys>>()
    var sysList: RespStateLiveData<List<Sys>> = _sysList


    fun getNavi() = currentScope.launch {
        repo.getNavi(_naviList)
    }

    fun getSys() = currentScope.launch {
        repo.getSys(_sysList)
    }

}