package com.oasis.app_home.viewmodel

import com.oasis.app_common.base.BaseViewModel
import com.oasis.app_common.base.UiState
import com.oasis.app_home.bean.Article
import com.oasis.app_home.bean.Banner
import com.oasis.app_home.repo.HomeRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(private val repo: HomeRepo) : BaseViewModel() {

    private var _bannerListFlow =
        MutableStateFlow<UiState<List<Banner>>>(UiState.Loading)
    var bannerListFlow: StateFlow<UiState<List<Banner>>> = _bannerListFlow.asStateFlow()


    private var _articleFlow = MutableStateFlow<UiState<Article>>(UiState.Loading)
    var articleFlow: StateFlow<UiState<Article>> = _articleFlow.asStateFlow()

    private var _collectFlow = MutableSharedFlow<UiState<String>>()
    var collectFlow: SharedFlow<UiState<String>> = _collectFlow.asSharedFlow()

    // flow
    fun getBannerByFlow() = safeApiCall(onSuccess = {
        it?.run { _bannerListFlow.emit(UiState.Success(this)) }
    }) {
        _bannerListFlow.emit(UiState.Loading)
        repo.getBanner()
    }

    fun getArticleByFlow(currentPage: Int) = safeApiCall(onSuccess = {
        it?.run { _articleFlow.emit(UiState.Success(this)) }
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