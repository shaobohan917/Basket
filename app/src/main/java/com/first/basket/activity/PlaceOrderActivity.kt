package com.first.basket.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.first.basket.R
import com.first.basket.adapter.PlaceOrderAdapter
import com.first.basket.app.BaseApplication
import com.first.basket.base.BaseActivity
import com.first.basket.bean.ProductsBean
import com.first.basket.common.StaticValue
import com.first.basket.utils.SPUtil
import kotlinx.android.synthetic.main.activity_place_order.*
import kotlinx.android.synthetic.main.layout_order_header.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import kotlin.collections.LinkedHashMap
import android.view.LayoutInflater
import android.widget.TextView


/**
 * Created by hanshaobo on 15/10/2017.
 */
class PlaceOrderActivity : BaseActivity() {

    private var mGoodsMap: LinkedHashMap<ProductsBean, Int> = LinkedHashMap()
    private var mGoodsList = ArrayList<ProductsBean>()

    private lateinit var mAdapter: PlaceOrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_order)
        initView()
        initData()

        initListener()
    }


    private fun initData() {
        mAdapter = PlaceOrderAdapter(this@PlaceOrderActivity, mGoodsList)
        recyclerView.adapter = mAdapter

        mGoodsMap = BaseApplication.getInstance().mGoodsMap

        var iterator = mGoodsMap.entries.iterator()
        while (iterator.hasNext()) {
            var entry = iterator.next() as Map.Entry<ProductsBean, Int>
            var key = entry.key
            var value = entry.value

            mGoodsList.add(key)
            Collections.reverse(mGoodsList)
        }

        setHeaderView()
        setFooterView()
        mAdapter.notifyDataSetChanged()
    }

    private fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setHeaderView() {
        val header = LayoutInflater.from(this).inflate(R.layout.layout_order_header, recyclerView, false)
        if (TextUtils.isEmpty(SPUtil.getString(StaticValue.USER_ADDRESS, ""))) {
            header.findViewById<TextView>(R.id.tvAddress).text = "请先添加收货地址"
        } else {
            header.findViewById<TextView>(R.id.tvName).text = SPUtil.getString(StaticValue.USER_NAME, "")
            header.findViewById<TextView>(R.id.tvPhone).text = SPUtil.getString(StaticValue.USER_PHONE, "")
            header.findViewById<TextView>(R.id.tvAddress).text = SPUtil.getString(StaticValue.USER_ADDRESS, "")
        }
        mAdapter.setHeaderView(header)

    }


    private fun setFooterView() {
        val footer = LayoutInflater.from(this).inflate(R.layout.layout_order_footer, recyclerView, false)
        var price = intent.getStringExtra("price")
        footer.findViewById<TextView>(R.id.tvPrice).text = price
        mAdapter.setFooterView(footer)

    }


    private fun initListener() {
        ivBuy.onClick {
            doPlaceOrder()
        }

    }

    private fun doPlaceOrder() {
        myStartActivity(OrderResultActivity::class.java, true)

//        HttpMethods.createService().doPlaceOrder("do_placeorder","","",SPUtil.getString(StaticValue.USER_ID,""),"")
//                .compose(TransformUtils.defaultSchedulers())
//
    }

}