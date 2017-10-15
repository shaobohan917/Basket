package com.first.basket.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.first.basket.R
import com.first.basket.adapter.PlaceOrderAdapter
import com.first.basket.app.BaseApplication
import com.first.basket.base.BaseActivity
import com.first.basket.bean.ProductsBean
import com.first.basket.common.StaticValue
import com.first.basket.utils.SPUtil
import kotlinx.android.synthetic.main.activity_place_order.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import kotlin.collections.LinkedHashMap

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

    private fun initView() {
        tvAddress.text = SPUtil.getString(StaticValue.SP_ADDRESS, "")
        recyclerView.layoutManager = LinearLayoutManager(this)


    }


    private fun initData() {

        mAdapter= PlaceOrderAdapter(this@PlaceOrderActivity, mGoodsList)
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
        mAdapter.notifyDataSetChanged()

    }



    private fun initListener() {
        ivBuy.onClick {
            doPlaceOrder()
        }

    }

    private fun doPlaceOrder() {

//        HttpMethods.createService().doPlaceOrder("do_placeorder","","",SPUtil.getString(StaticValue.USER_ID,""),"")
//                .compose(TransformUtils.defaultSchedulers())
//
    }

}