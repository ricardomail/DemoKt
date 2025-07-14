package com.oasis.app_home.viewmodel

import com.oasis.app_common.base.BaseViewModel
import com.oasis.app_home.bean.Article
import com.oasis.app_home.bean.Banner
import com.oasis.app_home.repo.HomeRepo
import com.oasis.app_network.okhttp.RespStateLiveData
import com.oasis.app_network.okhttp.RespStateMutableLiveData

class HomeViewModel(private val repo: HomeRepo) : BaseViewModel() {

    private var _bannerList = RespStateMutableLiveData<List<Banner>>()
    var bannerList: RespStateLiveData<List<Banner>> = _bannerList

    private var _article = RespStateMutableLiveData<Article>()
    var article: RespStateLiveData<Article> = _article

    private var _collectData = RespStateMutableLiveData<String>()
    var collectData: RespStateLiveData<String> = _collectData

    fun getBanner() = launch {
        repo.getBanner(_bannerList)
    }

    fun getArticle(currentPage: Int) = launch { repo.getArticle(currentPage, _article) }

    fun collectEvent(id: Int, detach: Boolean) = launch {
        if (detach) {
            repo.unCollect(id, _collectData)
        } else {
            repo.collect(id, _collectData)
        }
    }

}