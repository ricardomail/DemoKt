package com.oasis.mydemokt.ui

import android.os.Bundle
import android.widget.PopupWindow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.oasis.app_common.base.BaseActivity
import com.oasis.app_home.ui.HomeFragment
import com.oasis.app_me.ui.MyCollectFragment
import com.oasis.mydemokt.R
import com.oasis.mydemokt.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var fragmentList: MutableList<Fragment>
    var oldFragmentIndex = 0
    private var pw: PopupWindow? = null
    var f1: Fragment? = null
    var f2: Fragment? = null
    var f3: Fragment? = null
    var f4: Fragment? = null
    override fun getLayoutID(): Int = R.layout.activity_main

    override fun init() {
        mBind.ivDraw.setOnClickListener { mBind.dl.open() }
        mBind.ivBox.setOnClickListener { showPop() }
        mBind.bnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.f1 -> {
                    switchFragment(0)
                    mBind.fName = resources.getString(R.string.tab_home)
                    return@setOnItemSelectedListener true
                }

                R.id.f2 -> {
                    switchFragment(1)
                    mBind.fName = resources.getString(R.string.tab_project)
                    return@setOnItemSelectedListener true
                }

                R.id.f3 -> {
                    switchFragment(2)
                    mBind.fName = resources.getString(R.string.tab_navi)
                    return@setOnItemSelectedListener true
                }

                R.id.f4 -> {
                    switchFragment(3)
                    mBind.fName = resources.getString(R.string.tab_collect)
                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener false
                }
            }

        }
        initFragment()
        fragmentList = mutableListOf(f1!!, f2!!, f3!!, f4!!)
        switchFragment(0)
        mBind.fName = resources.getString(R.string.tab_home)
    }

    private fun initFragment() {
        if (f1 == null) {
            f1 = HomeFragment()
        }
        if (f2 == null) {
            f2 = HomeFragment()
        }
        if (f3 == null) {
            f3 = HomeFragment()
        }
//        if (f2 == null) {
//            f2 = ProjectFragment()
//        }
//        if (f3 == null) {
//            f3 = MainFragment()
//        }
        if (f4 == null) {
            f4 = MyCollectFragment()
        }
    }

    private fun switchFragment(index: Int) {
//        if (index != 0) return
        val targetFragment = fragmentList[index]
        val oldFragment = fragmentList[oldFragmentIndex]
        oldFragmentIndex = index
        val ft = supportFragmentManager.beginTransaction()
        if (oldFragment.isAdded) {
            ft.hide(oldFragment)
        }
        if (!targetFragment.isAdded) {
            ft.add(R.id.fl_content, targetFragment)
        }
        ft.show(targetFragment).commitAllowingStateLoss()
    }

    private fun showPop() {

    }


}