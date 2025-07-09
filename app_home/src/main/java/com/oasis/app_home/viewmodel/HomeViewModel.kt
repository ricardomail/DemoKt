package com.oasis.app_home.viewmodel

import com.oasis.app_common.base.BaseViewModel
import com.oasis.app_common.network.RespStateLiveData
import com.oasis.app_home.bean.Article
import com.oasis.app_home.bean.Banner
import com.oasis.app_home.repo.HomeRepo

class HomeViewModel(private val repo: HomeRepo) : BaseViewModel() {

    var bannerList = RespStateLiveData<List<Banner>>()
    var article = RespStateLiveData<Article>()
    var collectData = RespStateLiveData<String>()

    fun getBanner() = launch {
        repo.getBanner(bannerList)
    }

    fun getArticle(currentPage: Int) = launch { repo.getArticle(currentPage, article) }

    fun collectEvent(id: Int, detach: Boolean) = launch {
        if (detach) {
            repo.unCollect(id, collectData)
        } else {
            repo.collect(id, collectData)
        }
    }

}