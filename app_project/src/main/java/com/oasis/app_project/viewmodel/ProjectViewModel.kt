package com.oasis.app_project.viewmodel

import com.oasis.app_common.ScopeViewModel
import com.oasis.app_network.okhttp.RespStateLiveData
import com.oasis.app_network.okhttp.RespStateMutableLiveData
import com.oasis.app_project.bean.Project
import com.oasis.app_project.bean.ProjectType
import com.oasis.app_project.repo.ProjectRepo
import kotlinx.coroutines.launch

class ProjectViewModel(private val repo: ProjectRepo) : ScopeViewModel() {

    private val _proTypeList = RespStateMutableLiveData<List<ProjectType>>()
    val projectTypeList: RespStateLiveData<List<ProjectType>> = _proTypeList

    private val _proList = RespStateMutableLiveData<Project>()
    val proList: RespStateLiveData<Project> = _proList

    private val _collectData = RespStateMutableLiveData<String>()
    val collectData: RespStateLiveData<String> = _collectData

    fun getProTypeList() = currentScope.launch {
        repo.getProTypeList(_proTypeList)
    }

    fun getProList(currentPage: Int, cid: Int) = currentScope.launch {
        repo.getProList(currentPage, cid, _proList)
    }

    fun collectEvent(id: Int, detach: Boolean) = currentScope.launch {
        if (detach) {
            repo.unCollect(id, _collectData)
        } else {
            repo.collect(id, _collectData)
        }
    }

}