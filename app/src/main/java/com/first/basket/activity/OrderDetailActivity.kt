package com.first.basket.activity

import android.os.Bundle
import com.first.basket.base.BaseActivity
import com.first.basket.R

/**
 * Created by hanshaobo on 10/09/2017.
 */
class OrderDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        initData()
    }

    private fun initData() {

    }
}