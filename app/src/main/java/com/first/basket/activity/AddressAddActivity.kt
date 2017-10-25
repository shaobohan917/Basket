package com.first.basket.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.ActivityCompat
import android.text.TextUtils
import com.amap.api.services.core.PoiItem
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeQuery
import com.amap.api.services.geocoder.RegeocodeResult
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.base.HttpResult
import com.first.basket.bean.*
import com.first.basket.common.CommonMethod
import com.first.basket.common.StaticValue
import com.first.basket.db.ContactDao
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.LogUtils
import com.first.basket.utils.SPUtil
import com.first.basket.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_address_add.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by hanshaobo on 10/09/2017.
 * 权限申请：http://blog.csdn.net/yhaolpz/article/details/51290265
 */
class AddressAddActivity : BaseActivity() {
    private var from: Int = 0
    private lateinit var address: AddressBean

    private var poiItem: PoiItem? = null
    private var subdistrict: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_add)
        initData()
        initView()
        initListener()
    }

    private fun initListener() {
        ivContactSelect.onClick {
            checkPermission1()
        }
    }

    private fun initData() {
        from = intent.getIntExtra("from", 0)
        if (from == 1) {
            address = intent.getSerializableExtra("address") as AddressBean
            titleView.setTitle("修改地址")
        } else {
            titleView.setTitle("添加地址")
        }
    }

    private fun initView() {
        if (from == 1) {
            etName.setText(address.receiver)
            etPhone.setText(address.recvphone)
            if (address.street.contains("&")) {
                etAddress.setText(address.street.substring(0, address.street.indexOf("&")))
                etNumber.setText(address.street.substring(address.street.indexOf("&") + 1, address.street.length))
            } else {
                etAddress.setText(address.street)
            }

        }

        ivAddressSelect.onClick {
            startActivityForResult(Intent(this@AddressAddActivity, AddressMapsActivity::class.java), REQUEST_TWO)
        }
        btSave.onClick {
            if (TextUtils.isEmpty(etName.text)) {
                ToastUtil.showToast("请填写收货人姓名")
                return@onClick
            }
            if (!CommonMethod.isMobile(etPhone.text.toString())) {
                ToastUtil.showToast("请填写正确的手机号码")
                return@onClick
            }
            if (TextUtils.isEmpty(etAddress.text)) {
                ToastUtil.showToast("请填写地址")
                return@onClick
            }

            if (SPUtil.getBoolean(StaticValue.SP_LOGIN_STATUS, false)) {
                if (from == 1) {
                    //修改
                    modifyAddress()
                } else {
                    addAddress()
                }

            } else {
                ToastUtil.showToast("请登录!")
            }
        }
    }

    private fun saveToDb() {
        var user = ContactBean()
        user.address = etAddress.text.toString()
        user.phone = etPhone.text.toString()
        user.username = etName.text.toString()
        ContactDao.getInstance(this@AddressAddActivity).insertOrUpdateItem(user)
        LogUtils.d("保存成功！")
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode) {
            when (requestCode) {
                REQUEST_SPE ->
                    setUser(data)
                REQUEST_ONE -> {
                    val aoi = data?.extras?.get("aoiName")
                    val mapBean = data?.getSerializableExtra("mapBean") as MapBean
                    LogUtils.d("map:" + mapBean.aoiName + "," + mapBean.district + "," + mapBean.street + "," + mapBean.adCode)
                    etAddress.setText(aoi.toString())
                    anothre(mapBean.getLatitude(), mapBean.getLongitude())
                }
                REQUEST_TWO -> {
                    if (data != null) {
                        poiItem = data.getParcelableExtra("poiItem")
                        if (poiItem != null) {
                            geoGetStreet(poiItem!!)
                            etAddress.setText(poiItem!!.snippet + " " + poiItem!!.title)
                        }
                    }
                }
                REQUEST_THREE -> {
                    subdistrict = data?.getStringExtra("subdistrict")
                }
            }
        }
    }

    private fun geoGetStreet(poiItem: PoiItem) {
        searchGeo(poiItem)
    }


    /**
     * 逆地理
     */

    private fun searchGeo(poiItem: PoiItem) {
        val geocoderSearch = GeocodeSearch(this@AddressAddActivity)//传入context
        val latLonPoint = poiItem.latLonPoint
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系

        val query = RegeocodeQuery(latLonPoint, 200f, GeocodeSearch.AMAP)
        geocoderSearch.setOnGeocodeSearchListener(object : GeocodeSearch.OnGeocodeSearchListener {
            /**
             * 逆地理编码回调
             */
            override fun onRegeocodeSearched(result: RegeocodeResult, rCode: Int) {
                LogUtils.d("getcode:" + rCode)
                if (rCode == 1000) {
                    //拿到街道
                    var township = result.regeocodeAddress.township
                    LogUtils.d("街道：" + township)
                    getSubdistrict(poiItem.adName, township)
                }
            }

            override fun onGeocodeSearched(arg0: GeocodeResult, arg1: Int) {
                // TODO Auto-generated method stub
                LogUtils.d("onGeocodeSearched:" + arg0.geocodeAddressList[0].township)

            }

        })
        geocoderSearch.getFromLocationAsyn(query)
    }

    private fun setUser(data: Intent?) {
        val contacts = getPhoneContacts(data!!.data) as Array<*>
        etName.setText(contacts[0].toString())
        etPhone.setText(contacts[1].toString())
        etName.setSelection(contacts[0].toString().length)
        etPhone.setSelection(contacts[1].toString().length)
    }

    private fun checkPermission1() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_CONTACTS)) {
                    LogUtils.d("解释")

                } else {
                    ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.READ_CONTACTS), 0)
                }
            } else {
                doIfGranted()
            }
        } else {
            doIfGranted()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            doIfGranted()
        } else {
            ToastUtil.showToast(getString(R.string.no_granted))
        }
    }

    fun doIfGranted() {
        val uri = ContactsContract.Contacts.CONTENT_URI
        val intent = Intent(Intent.ACTION_PICK,
                uri)
        startActivityForResult(intent, REQUEST_SPE)
    }

    private fun getPhoneContacts(uri: Uri): Array<String?>? {
        val contact = arrayOfNulls<String>(2)
        //得到ContentResolver对象
        val cr = contentResolver
        //取得电话本中开始一项的光标
        val cursor = cr.query(uri, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            //取得联系人姓名
            val nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            contact[0] = cursor.getString(nameFieldColumnIndex)
            //取得电话号码
            val ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
            val phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null)
            if (phone != null) {
                phone.moveToFirst()
                contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            }
            phone!!.close()
            cursor.close()
        } else {
            return null
        }
        return contact
    }

    private fun anothre(latitude: Double, longitude: Double) {
        var geo = Geocoder(this@AddressAddActivity)
        var list = geo.getFromLocation(latitude, longitude, 10) as List<Address>
        for (i in 0 until list.size) {
            LogUtils.d(list[i].featureName)
        }


    }


    private fun getSubdistrict(district: String, township: String) {
        HttpMethods.createService().getSubdistrict("get_subdistrict", district)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<DistrictBean>>() {
                    override fun onNext(t: HttpResult<DistrictBean>) {
                        super.onNext(t)
                        //获取这个区的所有街道
//                        mDistrictDatas.addAll(t.result.data)
                        var list = t.result.data as ArrayList<DistrictBean.DataBean>
                        var isMatch = false
                        (0 until list.size)
                                .filter { township.equals(list[it].subdistrict) }
                                .forEach {
                                    subdistrict = list[it].subdistrictid
                                    LogUtils.d("匹配到街道：" + township + ",subdistrictid" + list[it].subdistrictid)
                                    isMatch = true
                                }
                        if (!isMatch) {
                            //如果是上海，跳转到选择接到列表
                            var intent = Intent(this@AddressAddActivity, StreetSelectActivity::class.java)
                            intent.putExtra("list", list)
                            myStartActivityForResult(intent, REQUEST_THREE)
                        }
                    }
                })
    }


    private fun addAddress() {
        var hashmap: HashMap<String, String?> = HashMap()
        hashmap.put("userid", SPUtil.getString(StaticValue.USER_ID, ""))
        hashmap.put("receiver", etName.text.toString())
        hashmap.put("recvphone", etPhone.text.toString())
        hashmap.put("address", etAddress.text.toString())
        hashmap.put("street", etNumber.text.toString())
        hashmap.put("village", "")
        hashmap.put("subdistrict", subdistrict)

        HttpMethods.createService().addAddress("do_addaddress", hashmap)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<LoginBean>>() {
                    override fun onNext(t: HttpResult<LoginBean>) {
                        super.onNext(t)
                        if (t.status == 0) {
                            setResult(Activity.RESULT_OK)
                            myFinish()
                        } else {
                            ToastUtil.showToast(this@AddressAddActivity, "fail")
                        }
                    }
                })

    }

    private fun modifyAddress() {
        var hashmap: HashMap<String, String?> = HashMap()
        hashmap.put("userid", SPUtil.getString(StaticValue.USER_ID, ""))
        hashmap.put("addressid", address.addressid)
        hashmap.put("receiver", etName.text.toString())
        hashmap.put("recvphone", etPhone.text.toString())
        hashmap.put("address", etAddress.text.toString())
        hashmap.put("street", etNumber.text.toString())
        hashmap.put("village", "")
        hashmap.put("subdistrict", subdistrict)

        HttpMethods.createService().addAddress("do_modifyaddress", hashmap)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<LoginBean>>() {
                    override fun onNext(t: HttpResult<LoginBean>) {
                        super.onNext(t)
                        ToastUtil.showToast(this@AddressAddActivity, t.info)
                        if (t.status == 0) {
                            setResult(Activity.RESULT_OK)
                            myFinish()
                        } else {
                            ToastUtil.showToast(this@AddressAddActivity, "fail")
                        }
                    }
                })
    }
}