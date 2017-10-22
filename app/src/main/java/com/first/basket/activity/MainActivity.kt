package com.first.basket.activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.KeyEvent
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.first.basket.R
import com.first.basket.app.BaseApplication
import com.first.basket.base.BaseActivity
import com.first.basket.bean.ProductBean
import com.first.basket.common.StaticValue
import com.first.basket.fragment.*
import com.first.basket.utils.LogUtils
import com.first.basket.utils.SPUtil
import com.first.basket.utils.ToastUtil
import com.google.gson.Gson
import com.roughike.bottombar.BottomBar
import com.roughike.bottombar.BottomBarTab
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


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

    lateinit var nearby: BottomBarTab

    var mChannel: Int = 1   //菜市，默认为社区菜市

    var mCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        instance = this
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
        //定位
        location()
        //从数据库中获取购物车中的商品
        var str = SPUtil.getString(StaticValue.GOODS_LIST, "")
        LogUtils.d("str:" + str)
        if (!TextUtils.isEmpty(str)) {
            val gson = GsonBuilder().create()
            val productListBean = gson.fromJson<ArrayList<ProductBean>>(str, object : TypeToken<ArrayList<ProductBean>>() {

            }.type)
            for (i in 0 until productListBean.size) {
                mCount += productListBean[i].amount
                LogUtils.d("ll:" + mCount)
                LogUtils.d("商品：" + productListBean[i].productname + "," + productListBean[i].amount)
            }
            BaseApplication.getInstance().setmProductsList(productListBean)

            nearby.setBadgeCount(mCount)
        }
    }

    private var exitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 1000) {
                ToastUtil.showToast("双击退出应用")
                exitTime = System.currentTimeMillis()

                var products = BaseApplication.getInstance().getmProductsList()
                var gson = Gson()
                var str = gson.toJson(products)
                SPUtil.setString(StaticValue.GOODS_LIST, str)
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
            mLocationClient.stopLocation()
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
        var dialog = AlertDialog.Builder(this@MainActivity)
        dialog.setTitle("提示")
        dialog.setMessage("您尚未登录，请先登录")
        dialog.setPositiveButton("去登录", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                myStartActivity(Intent(this@MainActivity, LoginActivity::class.java))
            }

        })
        dialog.setNegativeButton("取消", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                p0?.dismiss()
            }

        })
        dialog.show()
    }

    override fun onDestroy() {


        super.onDestroy()
//        LogUtils.d("保存：" + str)
//        showPop("确定退出？", "", "退出", object : DialogInterface.OnClickListener {
//            override fun onClick(p0: DialogInterface?, p1: Int) {
//                this@MainActivity.onDestroy()
//                this@MainActivity.super.onDestroy()
//            }
//        })

    }


    fun showPop(title: String, content: String, positive: String, listener: DialogInterface.OnClickListener) {
        var dialog = AlertDialog.Builder(this@MainActivity)
        dialog.setTitle(title)
        dialog.setMessage(content)
        dialog.setPositiveButton(positive, listener)
        dialog.setNegativeButton("取消", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                p0?.dismiss()
            }

        })
        dialog.show()
    }
}
