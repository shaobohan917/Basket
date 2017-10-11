package com.first.basket.activity

import android.os.Bundle
import com.first.basket.base.BaseActivity
import com.first.basket.R
import com.first.basket.fragment.*
import com.roughike.bottombar.BottomBar
import java.util.*

class MainActivity : BaseActivity() {
    private lateinit var bottomBar: BottomBar
    private var baseFragment = BaseFragment()
    private var fragmentList = ArrayList<BaseFragment>()

    private var homeFragment = HomeFragment()
    private var classifyFragment = ClassifyFragment()
    private var activeFragment = ActiveFragment()
    private var shopFragment = ShopFragment()
    private var mineFragment = MineFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var nearby = bottomBar.getTabWithId(R.id.tab_shop)
//        nearby.setBadgeCount(5)

        initView()
        initData()

    }

    private fun initView() {
        bottomBar = findViewById(R.id.bottombar)
        bottomBar.setTabTitleTextAppearance(10)

        fragmentList = ArrayList()
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