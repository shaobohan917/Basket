package com.first.basket.activity

import android.os.Bundle

import com.first.basket.R
import com.first.basket.base.BaseActivity
import kotlinx.android.synthetic.main.activity_order_result.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hanshaobo on 17/10/2017.
 */

class OrderResultActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_result)

        initView()
    }

    private fun initView() {
        tvGoHome.onClick {
            MainActivity.getInstance1().setCurrentPage(0)
            myFinish()
        }

    }
}
