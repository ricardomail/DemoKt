package com.oasis.app_navigation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.oasis.app_common.base.BaseFragment
import com.oasis.app_navigation.R
import com.oasis.app_navigation.adapter.NaviVPAdapter
import com.oasis.app_navigation.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>() {
    private var l: MutableList<String> = arrayListOf("导航", "体系")
    private var f: MutableList<Fragment> = arrayListOf(NaviFragment(), SysFragment())

    override fun getLayoutID(): Int = R.layout.fragment_main


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        mBind.viewPager.adapter = NaviVPAdapter(this, f)
        TabLayoutMediator(mBind.tabLayout, mBind.viewPager) { tab, position ->
            tab.text = l[position]
        }.attach()
    }

}