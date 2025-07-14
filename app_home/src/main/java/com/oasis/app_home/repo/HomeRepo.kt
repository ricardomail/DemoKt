package com.oasis.app_home.repo

import com.oasis.app_home.api.HomeApi
import com.oasis.app_home.bean.Article
import com.oasis.app_home.bean.Banner
import com.oasis.app_network.base.BaseRepository
import com.oasis.app_network.okhttp.RespStateMutableLiveData

private const val PAGE_SIZE = 10

class HomeRepo(private val api: HomeApi) : BaseRepository() {

    suspend fun getBanner(data: RespStateMutableLiveData<List<Banner>>) =
        dealResp(data) { api.getBanner() }


    suspend fun getArticle(currentPage: Int, data: RespStateMutableLiveData<Article>) = dealResp(data) {
        api.getArticleList(currentPage, PAGE_SIZE)
    }

    suspend fun collect(id: Int, data: RespStateMutableLiveData<String>) = dealResp(data) {
        api.collect(id)
    }

    suspend fun unCollect(id: Int, data: RespStateMutableLiveData<String>) = dealResp(data) {
        api.unCollect(id)
    }
}