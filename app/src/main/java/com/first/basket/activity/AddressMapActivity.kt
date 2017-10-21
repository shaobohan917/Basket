package com.first.basket.a

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps2d.*
import com.amap.api.maps2d.model.LatLng
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.bean.MapBean
import com.first.basket.common.StaticValue
import com.first.basket.utils.LogUtils
import com.first.basket.utils.SPUtil


/**
 * Created by hanshaobo on 30/08/2017.
 */
class AddressMapActivity : BaseActivity(), LocationSource, AMapLocationListener, PoiSearch.OnPoiSearchListener {


    private var mListener: LocationSource.OnLocationChangedListener? = null
    var aoiName: String? = null
    lateinit var district: String
    lateinit var street: String
    lateinit var adCode: String
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var mapBean = MapBean()


    override fun onLocationChanged(aMapLocation: AMapLocation?) {
        if (aMapLocation?.errorCode == 0) {
            var type = aMapLocation.locationType
            var country = aMapLocation.country
            var street = aMapLocation.street
            aoiName = aMapLocation.aoiName
            district = aMapLocation.district
            street = aMapLocation.street
            adCode = aMapLocation.adCode
            latitude = aMapLocation.latitude
            longitude = aMapLocation.longitude

            mapBean.aoiName = aoiName
            mapBean.district = district
            mapBean.street = street
            mapBean.adCode = adCode
            mapBean.latitude = latitude
            mapBean.longitude = longitude

            tvAddress.text = aoiName


            aMap.moveCamera(CameraUpdateFactory.zoomTo(100f))
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(LatLng(aMapLocation.latitude, aMapLocation.longitude)))
            mListener!!.onLocationChanged(aMapLocation)
            //获取定位信息

            //附近位置
            doSearchQuery(mapBean)
        }

    }

    private fun doSearchQuery(mapBean: MapBean) {
        var keyWord = mapBean.aoiName
        var query = PoiSearch.Query(keyWord, "", "北京市")
        query.setPageSize(20)// 设置每页最多返回多少条poiitem
        query.setPageNum(0)// 设置查第一页

//        var lp = LatLonPoint()
//        var poiSearch = PoiSearch(this, query)
//        poiSearch.setOnPoiSearchListener(this)
//        poiSearch.bound = PoiSearch.SearchBound(lp, 5000, true)//
//        poiSearch.searchPOIAsyn()// 异步搜索
    }

    override fun deactivate() {
        Log.d("luka", "deactivate")

    }


    override fun activate(p0: LocationSource.OnLocationChangedListener?) {
        this.mListener = p0
        Log.d("luka", "activate")
    }

    lateinit var mapView: MapView
    lateinit var mLocationClient: AMapLocationClient
    lateinit var aMap: AMap
    lateinit var settings: UiSettings

    lateinit var tvAddress: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_map)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)

        tvAddress = findViewById(R.id.tvAddress)
        tvAddress.setOnClickListener {
            SPUtil.setString(StaticValue.SP_ADDRESS, aoiName)
            intent.putExtra("aoiName", aoiName)
            intent.putExtra("mapBean", mapBean)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        initData()

    }

    private fun initData() {
        aMap = mapView.map
        settings = aMap.uiSettings
        aMap.setLocationSource(this)
        settings.isMyLocationButtonEnabled = true
        settings.isZoomControlsEnabled = true
        settings.logoPosition = AMapOptions.LOGO_POSITION_BOTTOM_LEFT
        settings.isScaleControlsEnabled = true

        aMap.isMyLocationEnabled = true

        location()
    }

    private fun location() {
        mLocationClient = AMapLocationClient(applicationContext)
        mLocationClient.setLocationListener(this)

        var mLocationOption = AMapLocationClientOption()
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        mLocationOption.isNeedAddress = true
        mLocationOption.isOnceLocation = true
        mLocationOption.isWifiActiveScan = true
        mLocationOption.interval = 2000
        mLocationClient.setLocationOption(mLocationOption)

        mLocationClient.startLocation()

    }

    private fun search(mapBean: MapBean) {
        var query = PoiSearch.Query(mapBean.aoiName, "商务住宅", "")
        query.pageSize = 10
        query.pageNum = 1

        var poiSearch = PoiSearch(this, query)
        poiSearch.bound = PoiSearch.SearchBound(LatLonPoint(mapBean.latitude,
                mapBean.longitude), 1000)//设置周边搜索的中心点以及区域
        poiSearch.setOnPoiSearchListener(this)

        poiSearch.searchPOIAsyn()

    }

    override fun onPoiItemSearched(p0: com.amap.api.services.core.PoiItem?, p1: Int) {
        LogUtils.d("onPoiItemSearched")
    }

    override fun onPoiSearched(p0: PoiResult, p1: Int) {
        LogUtils.d("onPoiSearched")
        var pois = p0.pois
        for (i in 0 until (pois.size)) {
            LogUtils.d("pos" + i + ":" + pois[i].adName)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
        mLocationClient.stopLocation()
        mLocationClient.onDestroy()
    }

}