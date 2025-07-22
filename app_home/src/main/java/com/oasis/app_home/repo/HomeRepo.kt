package com.oasis.app_home.repo

import com.oasis.app_home.api.HomeApi
import com.oasis.app_home.bean.Article
import com.oasis.app_home.bean.Banner
import com.oasis.app_common.base.BaseRepository
import com.oasis.app_common.base.RespStateMutableLiveData

private const val PAGE_SIZE = 10

class HomeRepo(private val api: HomeApi) : BaseRepository() {

    suspend fun getBanner(data: RespStateMutableLiveData<List<Banner>>) =
        dealResp(data) { api.getBanner() }


    suspend fun getArticle(currentPage: Int, data: RespStateMutableLiveData<Article>) =
        dealResp(data) {
            api.getArticleList(currentPage, PAGE_SIZE)
        }

    suspend fun collect(id: Int, data: RespStateMutableLiveData<String>) = dealResp(data) {
        api.collect(id)
    }

    suspend fun unCollect(id: Int, data: RespStateMutableLiveData<String>) = dealResp(data) {
        api.unCollect(id)
    }

    // flow
    suspend fun getBanner() = dealResp {
        api.getBanner()
    }

    suspend fun getArticle(currentPage: Int) = dealResp {
        api.getArticleList(currentPage, PAGE_SIZE)
    }

    suspend fun collect(id: Int) = dealResp {
        api.collect(id)
    }

    suspend fun unCollect(id: Int) = dealResp {
        api.unCollect(id)
    }
}