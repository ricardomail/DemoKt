package com.oasis.app_me.viewmodel

import com.oasis.app_common.base.BaseViewModel
import com.oasis.app_me.bean.MyCollect
import com.oasis.app_me.repo.MeRepo
import com.oasis.app_common.base.RespStateLiveData
import com.oasis.app_common.base.RespStateMutableLiveData

class MeViewModel(private val meRepo: MeRepo) : BaseViewModel() {

    private val _collectList = RespStateMutableLiveData<MyCollect>()
    val collectList: RespStateLiveData<MyCollect> = _collectList

    fun getCollectList(currentPage: Int) = launch {
        meRepo.getCollected(currentPage, _collectList)
    }
}