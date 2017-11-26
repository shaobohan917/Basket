package com.first.basket.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.first.basket.R
import com.first.basket.app.BaseApplication
import com.first.basket.base.BaseActivity
import com.first.basket.bean.ProductBean
import com.first.basket.common.CommonMethod
import com.first.basket.common.CommonMethod1
import com.first.basket.common.StaticValue
import com.first.basket.fragment.*
import com.first.basket.utils.LogUtils
import com.first.basket.utils.SPUtil
import com.first.basket.utils.ToastUtil
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.roughike.bottombar.BottomBar
import com.roughike.bottombar.BottomBarTab
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_loading.*
import java.util.*


class MainActivity : BaseActivity(), AMapLocationListener {
    companion object {
        private lateinit var instance: MainActivity
        fun getInstance1(): MainActivity {
            return instance
        }
    }

    private lateinit var bottomBar: BottomBar
    private var baseFragment = BaseFragment()
    private var fragmentList = ArrayList<BaseFragment>()

    private var homeFragment = HomeFragment()
    private var classifyFragment = ClassifyFragment()
    private var activeFragment = ActiveFragment()
    private var shopFragment = ShopFragment()
    private var mineFragment = MineFragment()

    private lateinit var nearby: BottomBarTab
    private lateinit var mLocationClient: AMapLocationClient
    var mChannel: Int = 1   //菜市，默认为社区菜市
    private var mCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        instance = this
        initView()
        initData()
    }

    fun setCountAdd() {
        mCount++
        nearby.setBadgeCount(mCount)
    }

    fun setCount() {
        var productListBean = BaseApplication.getInstance().getProductsList()
        var count = 0
        for (i in 0 until productListBean.size) {
            count += productListBean[i].amount
        }
        nearby.setBadgeCount(count)
        mCount = count
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
        //定位
        location()
        //从SP中获取购物车中的商品
        getProducts()

    }

    private fun getProducts() {
        var str = SPUtil.getString(StaticValue.GOODS_LIST, "")
        if (!TextUtils.isEmpty(str)) {
            val gson = GsonBuilder().create()
            val productListBean = gson.fromJson<ArrayList<ProductBean>>(str, object : TypeToken<ArrayList<ProductBean>>() {

            }.type)
            for (i in 0 until productListBean.size) {
                mCount += productListBean[i].amount
            }
            BaseApplication.getInstance().setProductsList(productListBean)

            nearby.setBadgeCount(mCount)
        }

    }

    private var exitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getCurrentPage() == 2 && (fragmentList[2] as ActiveFragment).setBack()) {
                return true
            } else if (event.action == KeyEvent.ACTION_DOWN) {
                if (System.currentTimeMillis() - exitTime > 1000) {
                    ToastUtil.showToast("双击退出应用")
                    exitTime = System.currentTimeMillis()
                    CommonMethod1.saveProduct()
                } else {
                    //保存购物车数据
                    finish()
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

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
            mLocationClient.stopLocation()
            (fragmentList[0] as HomeFragment).setLocation(aoiName)
        }
    }

    fun switchFragment(index: Int) {
        var fragment: BaseFragment? = null
        when (index) {
            0 -> fragment = homeFragment
            1 -> fragment = classifyFragment
            2 -> fragment = activeFragment
            3 -> fragment = shopFragment
            4 -> fragment = mineFragment
        }
        if (baseFragment == null) {
            replaceContent(fragment!!, R.id.fragmentContainer)
            baseFragment = fragment
        } else {
            if (fragment != null) {
                switchContent(baseFragment, fragment, R.id.fragmentContainer)
                baseFragment = fragment
            }
        }
    }

    fun setCurrentPage(index: Int) {
        if (fragmentList[index].isAdded) {
            switchFragment(index)
            bottomBar.selectTabAtPosition(index)
        }
    }

    fun showLogin() {
        showDialog("提示", "您尚未登录，请先登录", "去登录", DialogInterface.OnClickListener { p0, p1 -> myStartActivity(Intent(this@MainActivity, LoginActivity::class.java)) })
    }

    fun showLoading() {
        loadingView.visibility = View.VISIBLE
        loadingView.setIndeterminateDrawable(DoubleBounce())
    }

    fun hideLoading() {
        loadingView.visibility = View.GONE
    }


    fun goClassify(channel: Int) {
        mChannel = channel
        bottombar.selectTabAtPosition(1)
    }

    private fun getCurrentPage(): Int {
        return bottomBar.currentTabPosition
    }
}
