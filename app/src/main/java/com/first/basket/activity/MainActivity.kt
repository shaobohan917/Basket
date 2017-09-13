@file:Suppress("SENSELESS_COMPARISON")

package com.first.basket.activity

import android.os.Bundle
import android.widget.FrameLayout
import com.first.basket.base.BaseActivity
import com.first.basket.R
import com.first.basket.fragment.*
import com.roughike.bottombar.BottomBar
import java.util.*

class MainActivity : BaseActivity() {
    lateinit var bottomBar: BottomBar
    lateinit var fragmentContainer: FrameLayout

    var baseFragment = BaseFragment()
    var fragmentList = ArrayList<BaseFragment>()

    var homeFragment = HomeFragment()
    var classifyFragment = ClassifyFragment()

    var activeFragment = ActiveFragment()
    var shopFragment = ShopFragment()
    var mineFragment = MineFragment()

    //    val url  = "http://172.18.4.10:8080/page/"
    val url = "http://www.baidu.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomBar = findViewById<BottomBar>(R.id.bottombar)
        fragmentContainer = findViewById<FrameLayout>(R.id.fragmentContainer)

//        var nearby = bottomBar.getTabWithId(R.id.tab_shop)
//        nearby.setBadgeCount(5)

        initView()

        initData()

    }

    private fun initView() {
        fragmentList = ArrayList<BaseFragment>()
        homeFragment = HomeFragment()
        fragmentList.add(homeFragment)
        fragmentList.add(classifyFragment)
        fragmentList.add(activeFragment)
        fragmentList.add(shopFragment)
        fragmentList.add(mineFragment)

    }

    private fun initData() {
        var fragment = BaseFragment()
        bottomBar.setOnTabSelectListener { l ->
            when (l) {
                R.id.tab_home
                -> fragment = fragmentList.get(0)
                R.id.tab_classify
                -> fragment = fragmentList.get(1)
                R.id.tab_active
                -> fragment = fragmentList.get(2)
                R.id.tab_shop
                -> fragment = fragmentList.get(3)
                R.id.tab_mine
                -> fragment = fragmentList.get(4)
            }

            if (baseFragment == null) {
                replaceContent(fragment, R.id.fragmentContainer);
                baseFragment = fragment;
            } else {
                if (fragment != null) {
                    switchContent(baseFragment, fragment, R.id.fragmentContainer);
                    baseFragment = fragment;
                }
            }
        }

    }
}
