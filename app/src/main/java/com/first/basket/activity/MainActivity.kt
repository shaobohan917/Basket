package com.first.basket.activity

import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.bean.ContactBean
import com.first.basket.bean.ProductsBean
import com.first.basket.db.ContactDao
import com.first.basket.db.ProductDao
import com.first.basket.fragment.*
import com.first.basket.utils.LogUtils
import com.roughike.bottombar.BottomBar
import com.roughike.bottombar.BottomBarTab
import java.util.*
import kotlin.collections.ArrayList
import com.first.basket.common.StaticValue
import com.first.basket.utils.SPUtil
import com.first.basket.common.CommonMethod.hideKeyboard
import android.view.KeyEvent.KEYCODE_BACK
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.first.basket.utils.ToastUtil


class MainActivity : BaseActivity(), AMapLocationListener {


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
        location()
    }

    private var exitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 1000) {
                ToastUtil.showToast("双击退出应用")
                exitTime = System.currentTimeMillis()
            } else {
                //保存购物车数据
//                ProductDao.getInstance(this@MainActivity).insertOrUpdateItem()
                finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)

    }


    lateinit var mLocationClient: AMapLocationClient
    private fun location() {
        mLocationClient = AMapLocationClient(applicationContext)
        mLocationClient.setLocationListener(this)

        var mLocationOption = AMapLocationClientOption()
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        mLocationOption.isNeedAddress = true
        mLocationOption.isOnceLocation = false
        mLocationOption.isWifiActiveScan = true
        mLocationOption.isMockEnable = false
        mLocationOption.interval = 2000
        mLocationClient.setLocationOption(mLocationOption)

        mLocationClient.startLocation()
    }

    private lateinit var aoiName: String

    override fun onLocationChanged(aMapLocation: AMapLocation) {
        if (aMapLocation.errorCode == 0) {
            aoiName = aMapLocation.aoiName
            (fragmentList[0] as HomeFragment).setLocation(aoiName)

        }
    }
}
