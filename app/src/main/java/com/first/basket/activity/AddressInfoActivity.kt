package com.first.basket.activity

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import com.first.basket.R
import com.first.basket.a.AddressMapActivity
import com.first.basket.base.BaseActivity
import com.first.basket.base.HttpResult
import com.first.basket.bean.ContactBean
import com.first.basket.bean.DistrictBean
import com.first.basket.bean.LoginBean
import com.first.basket.bean.MapBean
import com.first.basket.common.StaticValue
import com.first.basket.db.ContactDao
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.LogUtils
import com.first.basket.utils.SPUtil
import com.first.basket.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_address_info.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hanshaobo on 10/09/2017.
 */
class AddressInfoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_info)
        initView()
    }

    private fun initView() {
        ivAddressSelect.onClick {
            startActivityForResult(Intent(this@AddressInfoActivity, AddressMapActivity::class.java), 0)
        }
        btSave.onClick {
            //            saveToDb()
//            var user = ContactBean()
//            user.address = etAddress.text.toString()
//            user.phone = etPhone.text.toString()
//            user.username = etContact.text.toString()
//
//            intent.putExtra("aoiName", etAddress.text)
//            SPUtil.setString(StaticValue.SP_ADDRESS, etAddress.text.toString())
//            setResult(Activity.RESULT_OK, intent)
//            finish()

            if (SPUtil.getBoolean(StaticValue.SP_LOGIN_STATUS, false)) {
                addAddress()
            } else {
                ToastUtil.showToast("请登录!")
            }
        }

    }

    private fun saveToDb() {
        var user = ContactBean()
        user.address = etAddress.text.toString()
        user.phone = etPhone.text.toString()
        user.username = etContact.text.toString()
        ContactDao.getInstance(this@AddressInfoActivity).insertOrUpdateItem(user)
        LogUtils.d("保存成功！")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        val aoi = data.extras.get("aoiName")
        val mapBean = data.getSerializableExtra("mapBean") as MapBean
        LogUtils.d("map:" + mapBean.aoiName + "," + mapBean.district + "," + mapBean.street+","+mapBean.adCode)
        etAddress.setText(aoi.toString())

        getSubdistrict(mapBean.district)

        anothre(mapBean.getLatitude(),mapBean.getLongitude())
    }

    private fun anothre(latitude: Double, longitude: Double) {
        var geo = Geocoder(this@AddressInfoActivity)
        var list = geo.getFromLocation(latitude,longitude,10) as List<Address>
        for (i in 0 until list.size){
            LogUtils.d(list[i].featureName)
        }


    }

    private fun getSubdistrict(district: String) {
        HttpMethods.createService().getSubdistrict("get_subdistrict",district )
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<DistrictBean>>() {
                    override fun onNext(t: HttpResult<DistrictBean>) {
                        super.onNext(t)

                    }

                })
    }


    private fun addAddress() {
        var hashmap: HashMap<String, String> = HashMap()
        hashmap.put("userid", SPUtil.getString(StaticValue.USER_ID, ""))
        hashmap.put("receiver", etContact.text.toString())
        hashmap.put("recvphone", etPhone.text.toString())
        hashmap.put("address", etAddress.text.toString())
        hashmap.put("street", "")
        hashmap.put("village", "")
        hashmap.put("subdistrict", "")

        HttpMethods.createService().addAddress("do_addaddress", hashmap)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<LoginBean>>() {
                    override fun onNext(t: HttpResult<LoginBean>) {
                        super.onNext(t)
                        if (t.status == 0) {
                        } else {
                            ToastUtil.showToast(this@AddressInfoActivity, "fail")
                        }
                    }
                })

    }
}