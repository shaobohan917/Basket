package com.first.basket.activity

import android.os.Bundle
import android.os.Handler
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.bean.ProductsBean
import com.first.basket.db.ProductDao
import com.first.basket.fragment.*
import com.first.basket.utils.LogUtils
import com.roughike.bottombar.BottomBar
import com.roughike.bottombar.BottomBarTab
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity() {
    private lateinit var bottomBar: BottomBar
    private var baseFragment = BaseFragment()
    private var fragmentList = ArrayList<BaseFragment>()

    private var homeFragment = HomeFragment()
    private var classifyFragment = ClassifyFragment()
    private var activeFragment = ActiveFragment()
    private var shopFragment = ShopFragment()
    private var mineFragment = MineFragment()

    lateinit var nearby: BottomBarTab

    var mCount = 0
    var mGoodsMap = LinkedHashMap<ProductsBean, Int>()   //添加到购物车的集合

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initData()
    }

    fun setCountAdd() {
        Handler().postDelayed({
            mCount++
            nearby.setBadgeCount(mCount)
        }, 500)
    }

    private fun initView() {
        bottomBar = findViewById(R.id.bottombar)
        bottomBar.setTabTitleTextAppearance(10)
        nearby = bottomBar.getTabWithId(R.id.tab_shop)

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

        test111()

    }

    private fun test111() {
        var product = ProductsBean()
        product.productid = "1"
        product.productname = "luka"
        product.price = "1.1"

        ProductDao.getInstance(this@MainActivity).insertOrUpdateItem(product)

        var list = ProductDao.getInstance(this@MainActivity).products
        LogUtils.d("li:"+list.size)


    }
}
