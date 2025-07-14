package com.oasis.app_me.ui

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.oasis.app_common.base.BaseVMFragment
import com.oasis.app_common.network.BaseStateObserver
import com.oasis.app_common.util.Constants
import com.oasis.app_me.R
import com.oasis.app_me.adapter.CollectRvAdapter
import com.oasis.app_me.bean.MyCollect
import com.oasis.app_me.bean.MyCollectDetail
import com.oasis.app_me.databinding.FragmentCollectBinding
import com.oasis.app_me.listener.CollectItemClickListener
import com.oasis.app_me.viewmodel.MeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyCollectFragment : BaseVMFragment<FragmentCollectBinding>() {
    private val meViewModel: MeViewModel by viewModel()
    private lateinit var collectRvAdapter: CollectRvAdapter
    private lateinit var lm: LinearLayoutManager
    private var isLoadMore = false
    private var list: MutableList<MyCollectDetail> = arrayListOf()

    override fun observe() {
        meViewModel.collectList.observe(this, collectObserver)
    }

    override fun init() {
        lm = LinearLayoutManager(activity)
        mBind.rvCollect.layoutManager = lm
        collectRvAdapter = CollectRvAdapter(listener)
        mBind.rvCollect.adapter = collectRvAdapter
        mBind.rvCollect.addOnScrollListener(scrollListener)
        mBind.srlCollect.setColorSchemeResources(com.oasis.app_common.R.color.theme_color)
        mBind.srlCollect.setOnRefreshListener {
            collectRvAdapter.isLastPage = false
            getCollectList(0)
        }
        getCollectList(0)
    }

    override fun getLayoutID(): Int = R.layout.fragment_collect


    fun getCollectList(index: Int) {
        meViewModel.getCollectList(index)
    }

    private fun resetUI() {
        isLoadMore = false
        if (mBind.srlCollect.isRefreshing) {
            mBind.srlCollect.isRefreshing = false
        }
    }

    private val collectObserver = object : BaseStateObserver<MyCollect>() {
        override fun getRespDataSuccess(data: MyCollect) {
            resetUI()
            currentPage = data.curPage - 1
            if (currentPage == 0) list.clear()
            if (data.over) collectRvAdapter.isLastPage = true
            list.addAll(data.datas)
            if (currentPage == 0) {
                collectRvAdapter.setData(null)
                collectRvAdapter.setData(list)
                lm.scrollToPosition(0)
            } else {
                collectRvAdapter.setData(list)
            }
        }

        override fun getRespDataEnd() {
            resetUI()
        }
    }

    private val listener = object : CollectItemClickListener {
        override fun onClick(position: Int) {
            val data = list[position]
            ARouter.getInstance().build(Constants.PATH_WEB)
                .withString(Constants.WEB_LINK, data.link)
                .withString(Constants.WEB_TITLE, data.title)
                .navigation()
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE && collectRvAdapter.itemCount != 0 &&
                (lm.findLastVisibleItemPosition() + 1) == collectRvAdapter.itemCount &&
                !isLoadMore && !collectRvAdapter.isLastPage
            ) {
                Log.d(TAG, "onScrollStateChanged: last-----")
                isLoadMore = true
                getCollectList(currentPage + 1)
            }
        }
    }
}