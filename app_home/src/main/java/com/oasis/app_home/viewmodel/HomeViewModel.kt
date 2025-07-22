package com.oasis.app_home.viewmodel

import com.oasis.app_common.base.BaseViewModel
import com.oasis.app_home.bean.Article
import com.oasis.app_home.bean.Banner
import com.oasis.app_home.repo.HomeRepo
import com.oasis.app_common.base.RespStateLiveData
import com.oasis.app_common.base.RespStateMutableLiveData
import com.oasis.app_common.base.UiState
import com.oasis.app_common.util.AppLogUtil
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(private val repo: HomeRepo) : BaseViewModel() {

    private var _bannerList = RespStateMutableLiveData<List<Banner>>()
    var bannerList: RespStateLiveData<List<Banner>> = _bannerList

    private var _article = RespStateMutableLiveData<Article>()
    var article: RespStateLiveData<Article> = _article

    private var _collectData = RespStateMutableLiveData<String>()
    var collectData: RespStateLiveData<String> = _collectData

    private var _bannerListFlow =
        MutableStateFlow<UiState<List<Banner>>>(UiState.Loading)
    var bannerListFlow: StateFlow<UiState<List<Banner>>> = _bannerListFlow.asStateFlow()


    private var _articleFlow = MutableStateFlow<UiState<Article>>(UiState.Loading)
    var articleFlow: StateFlow<UiState<Article>> = _articleFlow.asStateFlow()

    private var _collectFlow = MutableSharedFlow<UiState<String>>()
    var collectFlow: SharedFlow<UiState<String>> = _collectFlow.asSharedFlow()

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

    // flow
    // TODO: 可以在safeApiCall方法中增加个预方法，用来切换emit的状态，防止去重
    fun getBannerByFlow() = safeApiCall(onSuccess = {
        _bannerListFlow.emit(UiState.Success(it))
    }) {
        _bannerListFlow.emit(UiState.Loading)
        repo.getBanner()
    }

    fun getArticleByFlow(currentPage: Int) = safeApiCall(onSuccess = {
        _articleFlow.emit(UiState.Success(it))
    }, onError = {
        _articleFlow.emit(UiState.Error(it.message!!))
    }) {
        _articleFlow.emit(UiState.Loading)
        repo.getArticle(currentPage)
    }

    fun collectEventByFlow(id: Int, detach: Boolean) = safeApiCall(onSuccess = {
        _collectFlow.emit(UiState.Success(""))
    }, onError = {
        _collectFlow.emit(UiState.Error(""))
    }) {
        _collectFlow.emit(UiState.Loading)
        if (detach) {
            repo.unCollect(id)
        } else {
            repo.collect(id)
        }
    }

}