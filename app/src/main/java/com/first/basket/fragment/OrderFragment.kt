package com.first.basket.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.first.basket.R
import com.first.basket.activity.OrderDetailActivity
import com.first.basket.base.BaseRecyclerAdapter
import com.first.basket.base.HttpResult
import com.first.basket.bean.OrderListBean
import com.first.basket.common.CommonMethod
import com.first.basket.common.StaticValue
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.ImageUtils
import com.first.basket.utils.SPUtil
import com.first.basket.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.item_recycler_order.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

/**
 * Created by hanshaobo on 25/10/2017.
 */
class OrderFragment : BaseFragment() {
    private var mDatas = ArrayList<OrderListBean.DataBean>()
    private lateinit var mAdapter: BaseRecyclerAdapter<OrderListBean.DataBean, BaseRecyclerAdapter.ViewHolder<OrderListBean.DataBean>>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_order, container, false)!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        initData()
    }

    private fun initData() {
        mAdapter = BaseRecyclerAdapter(R.layout.item_recycler_order, mDatas) { view: View, item: OrderListBean.DataBean ->
            view.tvNO.text = resources.getString(R.string.order_number, item.strorderid)
            view.tvCost.text = resources.getString(R.string.order_price, item.qty, item.cost)
            if (item.orderdetail != null) {
                for (i in 0 until item.orderdetail.size) {
                    if (i > 3) break
                    var iv = ImageView(activity)
                    var params = ViewGroup.MarginLayoutParams(CommonMethod.dip2px(activity, 60f), CommonMethod.dip2px(activity, 60f))
                    params.setMargins(8, 8, 8, 8)
                    iv.layoutParams = params
                    ImageUtils.showImg(activity, item.orderdetail[i].img, iv)
                    view.llImg.addView(iv)
                }
            }
            view.llRoot.onClick {
                startActivity(Intent(activity, OrderDetailActivity::class.java))
            }
        }
        recyclerView.adapter = mAdapter

        getOrderList()
    }


    private fun getOrderList() {
        HttpMethods.createService()
                .getOrderList("get_orderlist", SPUtil.getString(StaticValue.USER_ID, ""))
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<OrderListBean>>() {
                    override fun onNext(t: HttpResult<OrderListBean>) {
                        super.onNext(t)
                        if (t.status == 0) {
                            setData(t.result.data)
                        } else {
                            ToastUtil.showToast(t.info)
                        }
                    }
                })
    }


    private fun setData(data: List<OrderListBean.DataBean>) {
        Collections.reverse(data)
        mDatas.addAll(data)
        mAdapter.notifyDataSetChanged()
    }
}