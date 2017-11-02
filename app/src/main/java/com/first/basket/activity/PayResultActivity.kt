package com.first.basket.activity

import android.os.Bundle

import com.first.basket.R
import com.first.basket.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pay_result.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hanshaobo on 17/10/2017.
 */

class PayResultActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_result)
        initData()
        initView()
    }

    private fun initData() {
        if (intent.getBooleanExtra("success", false)) {
            ivStatus.setImageDrawable(resources.getDrawable(R.mipmap.icon_order_success))
            tvStatus.text = "支付成功"
            titleView.setTitle("支付成功")
        } else {
            ivStatus.setImageDrawable(resources.getDrawable(R.mipmap.icon_order_fault))
            titleView.setTitle("支付失败")
            tvStatus.text = "支付失败"
        }
    }

    private fun initView() {
        tvGoHome.onClick {
            MainActivity.getInstance1().setCurrentPage(0)
            myFinish()
        }
    }
}
