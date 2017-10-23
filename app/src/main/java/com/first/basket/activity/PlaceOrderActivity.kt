package com.first.basket.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.first.basket.R
import com.first.basket.adapter.PlaceOrderAdapter
import com.first.basket.app.BaseApplication
import com.first.basket.base.BaseActivity
import com.first.basket.base.HttpResult
import com.first.basket.bean.AddressBean
import com.first.basket.bean.CodeBean
import com.first.basket.bean.ProductBean
import com.first.basket.common.StaticValue
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.LogUtils
import com.first.basket.utils.SPUtil
import com.first.basket.utils.ToastUtil
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_place_order.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*


/**
 * Created by hanshaobo on 15/10/2017.
 */
class PlaceOrderActivity : BaseActivity() {

    private var mGoodsList = ArrayList<ProductBean>()

    private lateinit var header: View
    private lateinit var footer: View
    private lateinit var addressInfo: AddressBean

    private lateinit var mAdapter: PlaceOrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_order)
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initData() {

        mGoodsList.addAll(BaseApplication.getInstance().isCheckProducts)
        mAdapter = PlaceOrderAdapter(this@PlaceOrderActivity, mGoodsList)
        recyclerView.adapter = mAdapter

        setHeader()
        setFooter()
        mAdapter.notifyDataSetChanged()
    }

    private fun setHeader() {
        header = LayoutInflater.from(this).inflate(R.layout.layout_order_header, recyclerView, false)
        var str = SPUtil.getString(StaticValue.DEFAULT_ADDRESS, "")
        addressInfo = GsonBuilder().create().fromJson(str, AddressBean::class.java)
        header.findViewById<TextView>(R.id.tvName).text = addressInfo.receiver
        header.findViewById<TextView>(R.id.tvPhone).text = addressInfo.recvphone
        header.findViewById<TextView>(R.id.tvAddress).text = addressInfo.street
        mAdapter.addHeaderView(header)
    }


    private fun setFooter() {
        footer = LayoutInflater.from(this).inflate(R.layout.layout_order_footer, recyclerView, false)
        var price = intent.getStringExtra("price")
        footer.findViewById<TextView>(R.id.tvPrice).text = price
        mAdapter.addFooterView(footer)

    }


    private fun initListener() {
        ivBuy.onClick {
            doPlaceOrder()
        }
    }

    private fun doPlaceOrder() {
        var productidString = StringBuilder()
        var numString = StringBuilder()

        for (i in 0 until mGoodsList.size) {
            productidString.append(mGoodsList[i].productid).append("|")
            numString.append(mGoodsList[i].amount).append("|")
        }
        val ps: String = productidString.toString().substring(0, productidString.length - 1)
        val ns: String = numString.toString().substring(0, numString.length - 1)

        HttpMethods.createService().doPlaceOrder("do_placeorder", ps, ns, SPUtil.getString(StaticValue.USER_ID, ""), addressInfo.addressid)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<CodeBean>>() {
                    override fun onNext(t: HttpResult<CodeBean>) {
                        super.onNext(t)
                        if (t.status == 0) {
                            //delete
                            deleteProducts()
                            myStartActivity(OrderResultActivity::class.java, true)
                        } else {
                            ToastUtil.showToast(t.info)
                        }
                    }
                })
    }

    private fun deleteProducts() {
        for (i in 0 until mGoodsList.size) {
            var product = BaseApplication.getInstance().getmProductsList().remove(mGoodsList[i])
            LogUtils.d("delete:" + product)
        }

    }

}