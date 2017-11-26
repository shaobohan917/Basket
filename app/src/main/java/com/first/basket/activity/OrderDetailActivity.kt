package com.first.basket.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.first.basket.R
import com.first.basket.adapter.PlaceOrderAdapter
import com.first.basket.base.BaseActivity
import com.first.basket.bean.OrderListBean
import com.first.basket.bean.ProductBean
import kotlinx.android.synthetic.main.activity_order_detail.*
import java.util.*

/**
 * Created by hanshaobo on 10/09/2017.
 */
class OrderDetailActivity : BaseActivity() {
    private lateinit var header: View
    private lateinit var footer: View
    private lateinit var orderBean: OrderListBean.DataBean
    private lateinit var mAdapter: PlaceOrderAdapter
    private var mGoodsList = ArrayList<ProductBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        initView()
        initData()
    }

    private fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        setHeader()
    }


    private fun setHeader() {
        header = LayoutInflater.from(this).inflate(R.layout.layout_order_header, recyclerView, false)

    }

    private fun initData() {
        orderBean = intent.getSerializableExtra("orderBean") as OrderListBean.DataBean
        mGoodsList = orderBean.orderdetail

        mAdapter = PlaceOrderAdapter(this@OrderDetailActivity, mGoodsList)
        recyclerView.adapter = mAdapter
        mAdapter.addHeaderView(header)

        header.findViewById<TextView>(R.id.tvOrderId).text = orderBean.orderid
    }
}