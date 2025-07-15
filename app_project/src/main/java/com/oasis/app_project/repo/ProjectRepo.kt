package com.oasis.app_project.repo

import com.oasis.app_network.base.BaseRepository
import com.oasis.app_network.okhttp.RespStateMutableLiveData
import com.oasis.app_project.api.ProjectApi
import com.oasis.app_project.bean.Project
import com.oasis.app_project.bean.ProjectType
import com.xj.anchortask.library.log.LogUtils

private const val PAGE_SIZE = 10

class ProjectRepo(private val api: ProjectApi) : BaseRepository() {

    suspend fun getProTypeList(data: RespStateMutableLiveData<List<ProjectType>>) = dealResp(data) {
        api.getProType()
    }

    suspend fun getProList(currentPage: Int, cid: Int, data: RespStateMutableLiveData<Project>){
        dealResp(data) {
            api.getProList(currentPage, PAGE_SIZE, cid)
        }
    }


    suspend fun collect(id: Int, data: RespStateMutableLiveData<String>) = dealResp(data) {
        api.collect(id)
    }

    suspend fun unCollect(id: Int, data: RespStateMutableLiveData<String>) = dealResp(data) {
        api.unCollect(id)
    }
}