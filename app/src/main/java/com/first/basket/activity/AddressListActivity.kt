package com.first.basket.activity

import android.os.Bundle
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.db.ContactDao
import kotlinx.android.synthetic.main.activity_address_list.*

/**
 * Created by hanshaobo on 10/09/2017.
 */
class AddressListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_list)
        initView()
    }

    private fun initView() {
        var list = ContactDao.getInstance(this@AddressListActivity).contacts
        for (i in 0 until list.size) {
            tvTest.text = list[i].address
        }

    }
}