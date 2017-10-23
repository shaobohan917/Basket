package com.first.basket.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.base.BaseRecyclerAdapter
import com.first.basket.bean.OrderListBean
import com.first.basket.common.StaticValue
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.SPUtil
import kotlinx.android.synthetic.main.activity_orderlist.*
import kotlinx.android.synthetic.main.item_recycler_order.view.*
import java.util.*

/**
 * Created by hanshaobo on 12/09/2017.
 */
class OrderListActivity : BaseActivity() {
    private var mDatas = ArrayList<OrderListBean.ResultBean.DataBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orderlist)
        initView()
        initData()
    }

    private fun initData() {
        getOrderList(SPUtil.getString(StaticValue.USER_ID, ""))
    }

    private fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        var adapter = BaseRecyclerAdapter(R.layout.item_recycler_order, mDatas) { view: View, item: OrderListBean.ResultBean.DataBean ->
            //            tvStatus.text = resources.getString(R.string.order_limit_time, item.delievedt)
//            tvCost.text = resources.getString(R.string.order_price, item.qty, item.cost)

        }
        recyclerView.adapter = adapter
    }

    private fun getOrderList(userid: String) {
        HttpMethods.createService().getOrderList("get_orderlist", userid)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<OrderListBean>() {
                    override fun onNext(t: OrderListBean) {
                        super.onNext(t)
                        setData(t.result.data)
                    }
                })
    }


    private fun setData(data: List<OrderListBean.ResultBean.DataBean>) {
        Collections.reverse(data)
        mDatas.addAll(data)

        recyclerView.layoutManager = LinearLayoutManager(this)
        var adapter = BaseRecyclerAdapter(R.layout.item_recycler_order, mDatas) { view: View, item: OrderListBean.ResultBean.DataBean ->
            view.tvLimitTime.text = resources.getString(R.string.order_limit_time, item.orderdt)
            view.tvCost.text = resources.getString(R.string.order_price, item.qty, item.cost)
        }
        recyclerView.adapter = adapter
    }
}