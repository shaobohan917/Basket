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
import com.first.basket.BaseActivity
import com.first.basket.R
import kotlinx.android.synthetic.main.activity_address.*


/**
 * Created by hanshaobo on 30/08/2017.
 */
class AddressActivity : BaseActivity(), LocationSource, AMapLocationListener {

    private var mListener: LocationSource.OnLocationChangedListener? = null
    lateinit var aoiName: String


    override fun onLocationChanged(aMapLocation: AMapLocation?) {
        if (aMapLocation?.errorCode == 0) {
            var type = aMapLocation.locationType
            var country = aMapLocation.country
            var street = aMapLocation.street
            aoiName = aMapLocation.aoiName

            tvAddress.text = aoiName


            aMap.moveCamera(CameraUpdateFactory.zoomTo(80f))
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(LatLng(aMapLocation.latitude,aMapLocation.longitude)))
            mListener!!.onLocationChanged(aMapLocation)
            //获取定位信息
        }

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
        setContentView(R.layout.activity_address)
        mapView = findViewById<MapView>(R.id.mapView)
        mapView.onCreate(savedInstanceState)

        tvAddress = findViewById<TextView>(R.id.tvAddress)
        tvAddress.setOnClickListener {
            intent.putExtra("aoiName",aoiName)
            setResult(Activity.RESULT_OK,intent)
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
        mLocationOption.isOnceLocation = false
        mLocationOption.isWifiActiveScan = true
        mLocationOption.isMockEnable = false
        mLocationOption.interval = 2000
        mLocationClient.setLocationOption(mLocationOption)

        mLocationClient.startLocation()


    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
        mLocationClient.stopLocation()
        mLocationClient.onDestroy()
    }

}