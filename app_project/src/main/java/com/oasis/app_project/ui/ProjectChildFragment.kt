package com.oasis.app_project.ui

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oasis.app_common.base.BaseVMFragment
import com.oasis.app_common.util.LoadingViewUtil
import com.oasis.app_common.util.ToastUtil
import com.oasis.app_network.base.BaseStateObserver
import com.oasis.app_project.R
import com.oasis.app_project.adapter.ProRVAdapter
import com.oasis.app_project.bean.Project
import com.oasis.app_project.bean.ProjectDetail
import com.oasis.app_project.databinding.FragmentProjectChildBinding
import com.oasis.app_project.listener.ProItemClickListener
import com.oasis.app_project.viewmodel.ProjectViewModel
import com.xj.anchortask.library.log.LogUtils
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class ProjectChildFragment private constructor() : BaseVMFragment<FragmentProjectChildBinding>(),
    ProItemClickListener {
    var collectPosition: Int = 0

    companion object {
        private const val C_ID: String = "cid"
        private const val INDEX: String = "index"

        fun newInstance(cid: Int, index: Int): ProjectChildFragment {
            return ProjectChildFragment().apply {
                val bundle = Bundle()
                bundle.putInt(C_ID, cid)
                bundle.putInt(INDEX, index)
                arguments = bundle
            }
        }
    }

    private var currentId: Int = 0
    private var currentIndex: Int = 0
    private val projectViewModel: ProjectViewModel by viewModel()
    private lateinit var proRVAdapter: ProRVAdapter
    private lateinit var lm: LinearLayoutManager
    private var isLoadMore = false
    private var list: MutableList<ProjectDetail> = arrayListOf()

    override fun observe() {
        projectViewModel.proList.observe(this, proListObserver)
        projectViewModel.collectData.observe(this, collectDataObserver)
    }


    override fun init() {
        LogUtils.d("load", "fragment init cid: $currentId")
        arguments?.let {
            currentId = it.getInt(C_ID)
            currentIndex = it.getInt(INDEX)
        }
        lm = LinearLayoutManager(activity)
        mBind.rvPro.layoutManager = lm
        proRVAdapter = ProRVAdapter(this)
        mBind.rvPro.adapter = proRVAdapter
        mBind.rvPro.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && proRVAdapter.itemCount != 0 &&
                    (lm.findLastVisibleItemPosition() + 1 == proRVAdapter.itemCount &&
                            !isLoadMore && !proRVAdapter.isLastPage)
                ) {
                    Log.d(TAG, "onScrollStateChanged: last-----")
                    isLoadMore = true
                    lazyLoad()
                }
            }
        })

        mBind.srlPro.setColorSchemeResources(com.oasis.app_common.R.color.theme_color)
        mBind.srlPro.setOnRefreshListener {
            proRVAdapter.isLastPage = false
            projectViewModel.getProList(1, currentId)
        }
//        projectViewModel.getProList(1, currentId)

    }


    override fun getLayoutID(): Int = R.layout.fragment_project_child

    private val proListObserver = object : BaseStateObserver<Project>() {
        override fun getRespDataSuccess(data: Project) {
            resetUI()
            currentPage = data.curPage
            if (currentPage == 1) {
                list.clear()
            }
            if (data.over) {
                proRVAdapter.isLastPage = true
            }
            list.addAll(data.datas)
            if (currentPage == 1) {
                proRVAdapter.setData(null)
                proRVAdapter.setData(list)
                lm.scrollToPosition(0)
            } else {
                proRVAdapter.setData(list)
            }
        }

        override fun getRespDataEnd() {
            resetUI()
        }
    }

    private val collectDataObserver = object : BaseStateObserver<String>() {
        override fun getRespDataStart() {
            LoadingViewUtil.showLoadingDialog(requireContext(), true)
        }

        override fun getRespDataEnd() {
            LoadingViewUtil.dismissLoadingDialog()
        }

        override fun getRespSuccess() {
            LoadingViewUtil.dismissLoadingDialog()
            ToastUtil.showMsg(if (list[collectPosition].collect) "取消收藏" else "收藏成功")
            list[collectPosition].collect = !list[collectPosition].collect
            proRVAdapter.notifyItemChanged(collectPosition)
        }
    }

    private fun resetUI() {
        isLoadMore = false//加载更多完成，重置false
        if (mBind.srlPro.isRefreshing) {
            mBind.srlPro.isRefreshing = false
        }
    }

    override fun onItemClick(position: Int) {

    }

    override fun onCollectClick(position: Int) {
        collectPosition = position
        projectViewModel.collectEvent(list[position].id, list[position].collect)
    }

    override fun lazyLoad() {
        projectViewModel.getProList(currentPage + 1, currentId)
    }

    override fun onDestroy() {
        LogUtils.d("load", "fragment destory cid: $currentId")
        super.onDestroy()
    }
}