package com.first.basket.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.first.basket.R
import com.first.basket.a.AddressActivity
import com.first.basket.base.BaseActivity
import com.first.basket.bean.UserBean
import com.first.basket.common.StaticValue
import com.first.basket.utils.SPUtil
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
            startActivityForResult(Intent(this@AddressInfoActivity, AddressActivity::class.java), 0)
        }
        btSave.onClick {
            var user = UserBean()
            user.address = etAddress.text.toString()
            user.phone = etPhone.text.toString()
            user.username = etContact.text.toString()

            intent.putExtra("aoiName", etAddress.text)
            SPUtil.setString(StaticValue.SP_ADDRESS,etAddress.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val aoi = data?.extras?.get("aoiName")
        etAddress.setText(aoi.toString())
    }
}