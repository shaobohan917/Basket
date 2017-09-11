package com.first.basket.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.first.basket.BaseActivity
import com.first.basket.R
import com.first.basket.a.AddressActivity
import com.first.basket.utils.LogUtils
import kotlinx.android.synthetic.main.activity_address_info.*
import kotlinx.android.synthetic.main.fragment_home.*
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
            intent.putExtra("aoiName", etAddress.text)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val aoi = data?.extras?.get("aoiName")
        etAddress.setText(aoi.toString())
        LogUtils.d("" + aoi)
    }
}