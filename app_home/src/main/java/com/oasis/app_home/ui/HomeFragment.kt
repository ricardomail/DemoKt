package com.oasis.app_home.ui

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.alibaba.android.arouter.launcher.ARouter
import com.oasis.app_common.base.BaseVMFragment
import com.oasis.app_common.util.Constants
import com.oasis.app_common.util.ToastUtil
import com.oasis.app_home.R
import com.oasis.app_home.adapter.BannerAdapter
import com.oasis.app_home.adapter.HomeRVAdapter
import com.oasis.app_home.bean.Article
import com.oasis.app_home.bean.ArticleDetail
import com.oasis.app_home.bean.Banner
import com.oasis.app_home.databinding.FragmentHomeBinding
import com.oasis.app_home.listener.HomeItemClickListener
import com.oasis.app_home.viewmodel.HomeViewModel
import com.oasis.app_common.base.BaseStateObserver
import com.oasis.app_common.base.UiState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseVMFragment<FragmentHomeBinding>(), HomeItemClickListener {
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var lm: LinearLayoutManager
    private var list: MutableList<ArticleDetail> = arrayListOf()
    private var collectPosition = 0
    private lateinit var homeRVAdapter: HomeRVAdapter
    private var isLoadMore = false


    override fun observe() {
//        homeViewModel.bannerList.observe(this, bannerObserver)
//        homeViewModel.article.observe(this, articleObserver)
//        homeViewModel.collectData.observe(this, collectObserver)
        lifecycleScope.launch {

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    homeViewModel.bannerListFlow.filter { it is UiState.Success }.map {
                        (it as UiState.Success).data
                    }.filterNotNull().filter { it.isNotEmpty() }.collect { result ->
                        mBind.topView.refreshData(result)
                    }
                }
            }

            launch {
                homeViewModel.articleFlow.collect { state ->
                    handleArticleData(state)
                }
            }

            launch {
                homeViewModel.collectFlow.collect { state ->
                    handleCollectData(state)
                }
            }

        }

    }

    override fun init() {
        mBind.topView.apply {
            setAdapter(BannerAdapter())
            registerLifecycleObserver(lifecycle)
            setScrollDuration(600)
            setInterval(5000)
            setAutoPlay(false)
        }.create()
        lm = LinearLayoutManager(activity)
        mBind.bottomView.layoutManager = lm
        homeRVAdapter = HomeRVAdapter(this)
        mBind.bottomView.adapter = homeRVAdapter
        mBind.bottomView.addOnScrollListener(scrollListener)
        mBind.srlHome.setColorSchemeResources(com.oasis.app_common.R.color.theme_color)
        mBind.srlHome.setOnRefreshListener {
            homeRVAdapter.isLastPage = false
            currentPage = 0
            getHomeData()
        }
        getHomeData()
    }

    private fun getHomeData() {
        homeViewModel.getBannerByFlow()
        homeViewModel.getArticleByFlow(currentPage)
    }

    override fun getLayoutID(): Int = R.layout.fragment_home
    override fun onItemClick(position: Int) {
        val data = list[position]
        ARouter.getInstance().build(Constants.PATH_WEB).withString(Constants.WEB_LINK, data.link)
            .withString(Constants.WEB_TITLE, data.title).navigation()
    }

    private val scrollListener = object : OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE && homeRVAdapter.itemCount != 0 &&
                (lm.findLastVisibleItemPosition() + 1) == homeRVAdapter.itemCount && !isLoadMore && !homeRVAdapter.isLastPage
            ) {
                Log.d(TAG, "onScrollStateChanged: last-----")
                isLoadMore = true
                homeViewModel.getArticleByFlow(currentPage + 1)
            }
        }
    }

    private val bannerObserver = object : BaseStateObserver<List<Banner>>() {
        override fun getRespDataSuccess(data: List<Banner>) {
            if (data.isNotEmpty()) {
                mBind.topView.refreshData(data)
            }
        }

        override fun getRespDataEnd() {
            resetUI()
        }
    }


    private val articleObserver = object : BaseStateObserver<Article>() {
        override fun getRespDataSuccess(data: Article) {
            resetUI()
            currentPage = data.curPage - 1
            // 下拉刷新
            if (currentPage == 0) list.clear()
            // 最后一页
            if (data.over) homeRVAdapter.isLastPage = true

            list.addAll(data.datas)
            if (currentPage == 0) {
                homeRVAdapter.setData(null)
                homeRVAdapter.setData(list)
                lm.scrollToPosition(0)
            } else {
                homeRVAdapter.setData(list)
            }
        }

        override fun getRespDataEnd() {
            resetUI()
        }
    }

    private val collectObserver = object : BaseStateObserver<String>() {
        override fun getRespDataStart() {
            showLoadingDialog()
        }

        override fun getRespDataEnd() {
            dismissLoadingDialog()
        }

        override fun getRespSuccess() {
            dismissLoadingDialog()
            list[collectPosition].collect = if (list[collectPosition].collect) {
                ToastUtil.showMsg("取消收藏")
                false
            } else {
                ToastUtil.showMsg("收藏成功")
                true
            }
            homeRVAdapter.notifyItemChanged(collectPosition)
        }
    }

    override fun onCollectClick(position: Int) {
        collectPosition = position
        homeViewModel.collectEventByFlow(list[position].id, list[collectPosition].collect)
    }

    private fun resetUI() {
        isLoadMore = false//加载更多完成，重置false
        if (mBind.srlHome.isRefreshing) {
            mBind.srlHome.isRefreshing = false
        }
    }

    // TODO: 感觉这个属于重复代码，可以搞一搞
    private fun handleCollectData(state: UiState<String>) {
        when (state) {
            is UiState.Loading -> showLoadingDialog()
            is UiState.Success -> {
                dismissLoadingDialog()
                list[collectPosition].collect = if (list[collectPosition].collect) {
                    ToastUtil.showMsg("取消收藏")
                    false
                } else {
                    ToastUtil.showMsg("收藏成功")
                    true
                }
                homeRVAdapter.notifyItemChanged(collectPosition)
            }

            is UiState.Error -> {
                dismissLoadingDialog()
                ToastUtil.showMsg("收藏失败")
            }
        }
    }

    private fun handleArticleData(state: UiState<Article>) {
        when (state) {
            is UiState.Loading -> {}

            is UiState.Success -> {
                val data = state.data
                resetUI()
                currentPage = data.curPage - 1
                // 下拉刷新
                if (currentPage == 0) list.clear()
                // 最后一页
                if (data.over) homeRVAdapter.isLastPage = true

                list.addAll(data.datas)
                if (currentPage == 0) {
                    homeRVAdapter.setData(null)
                    homeRVAdapter.setData(list)
                    lm.scrollToPosition(0)
                } else {
                    homeRVAdapter.setData(list)
                }
            }

            is UiState.Error -> {
                resetUI()
            }
        }
    }
}