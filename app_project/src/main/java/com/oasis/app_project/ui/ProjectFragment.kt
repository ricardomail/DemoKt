package com.oasis.app_project.ui

import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.oasis.app_common.base.BaseVMFragment
import com.oasis.app_common.base.BaseStateObserver
import com.oasis.app_project.R
import com.oasis.app_project.adapter.ProVPAdapter
import com.oasis.app_project.bean.ProjectType
import com.oasis.app_project.databinding.FragmentProjectBinding
import com.oasis.app_project.viewmodel.ProjectViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProjectFragment : BaseVMFragment<FragmentProjectBinding>() {
    private val projectViewModel: ProjectViewModel by viewModel()
    private var l: MutableList<String> = arrayListOf()
    private var f: MutableList<Fragment> = arrayListOf()

    override fun observe() {
        projectViewModel.projectTypeList.observe(
            this,
            object : BaseStateObserver<List<ProjectType>>() {
                override fun getRespDataSuccess(data: List<ProjectType>) {
                    initTab(data)
                }
            })
    }

    private fun initTab(data: List<ProjectType>) {
        data.forEachIndexed { index, projectType ->
            l.add("${(index + 1)}.${projectType.name}")
            f.add(ProjectChildFragment.newInstance(projectType.id, index))
        }
        mBind.viewpager.adapter = ProVPAdapter(this, f)
        mBind.viewpager.offscreenPageLimit = 3
        TabLayoutMediator(mBind.tabLayout, mBind.viewpager) { tab, position ->
            tab.text = l[position]
        }.attach()
    }

    override fun init() {
        projectViewModel.getProTypeList()
    }

    override fun getLayoutID(): Int = R.layout.fragment_project
}