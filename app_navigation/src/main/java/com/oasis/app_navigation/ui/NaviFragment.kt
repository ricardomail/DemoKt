package com.oasis.app_navigation.ui

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.oasis.app_common.base.BaseVMFragment
import com.oasis.app_common.util.Constants
import com.oasis.app_navigation.R
import com.oasis.app_navigation.adapter.NaviRVAdapter
import com.oasis.app_navigation.bean.Navi
import com.oasis.app_navigation.databinding.FragmentSysBinding
import com.oasis.app_navigation.viewmodel.NaviViewModel
import com.oasis.app_common.base.BaseStateObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

class NaviFragment : BaseVMFragment<FragmentSysBinding>() {
    private val naviViewModel: NaviViewModel by viewModel()

    private val txList = arrayListOf<View>()
    private var oldPosition = -1
    private lateinit var naviRVAdapter: NaviRVAdapter
    private val myData = arrayListOf<Navi>()
    private lateinit var container: MainFragment
    private lateinit var lm: LinearLayoutManager
    override fun observe() {
        container = parentFragment as MainFragment
        naviViewModel.naviList.observe(this, object : BaseStateObserver<List<Navi>>() {
            override fun getRespDataSuccess(data: List<Navi>) {
                for (i in 0..19) {
                    myData.add(data[i])
                }
                addView(myData)
                naviRVAdapter.setData(myData)
            }
        })
    }

    private fun addView(list: List<Navi>) {

        for (i in list.indices) {
            val layout = LayoutInflater.from(activity).inflate(R.layout.item_sys, null, false)
            layout.tag = i
            layout.setOnClickListener {
                setColor(it.tag as Int)
                lm.scrollToPositionWithOffset(it.tag as Int, 0)
            }

            val t = layout.findViewById<TextView>(R.id.tx_sys)
            txList.add(t)
            t.text = list[i].name

            if (i == 0) {
                setColor(i)
            }

            mBind.llSys.addView(layout)
        }

    }

    private fun setColor(position: Int) {
        if (oldPosition == position) return
        if (oldPosition != -1) {
            txList[oldPosition].background =
                ResourcesCompat.getDrawable(resources, R.drawable.shape_10dp_white, null)
        }

        oldPosition = position
        txList[oldPosition].background =
            ResourcesCompat.getDrawable(resources, R.drawable.shape_10dp_grey, null)
    }

    override fun init() {
        lm = LinearLayoutManager(activity)
        mBind.rvSys.layoutManager = lm
        naviRVAdapter = NaviRVAdapter {
            val data = myData[it.p1].articles[it.p2]
            ARouter.getInstance()
                .build(Constants.PATH_WEB)
                .withString(Constants.WEB_LINK, data.link)
                .withString(Constants.WEB_TITLE, data.title)
                .navigation()
        }
        mBind.rvSys.adapter = naviRVAdapter
        naviViewModel.getNavi()
    }

    override fun getLayoutID(): Int = R.layout.fragment_sys
}