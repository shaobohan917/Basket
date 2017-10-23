package com.first.basket.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
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
import com.first.basket.utils.SPUtil
import com.first.basket.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_place_order.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import kotlin.collections.LinkedHashMap


/**
 * Created by hanshaobo on 15/10/2017.
 */
class PlaceOrderActivity : BaseActivity() {

    private var mGoodsMap: LinkedHashMap<ProductBean, Int> = LinkedHashMap()
    private var mGoodsList = ArrayList<ProductBean>()

    private lateinit var header: View
    private lateinit var footer: View
    private lateinit var address1: TextView

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
            var entry = iterator.next() as Map.Entry<ProductBean, Int>
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
        header = LayoutInflater.from(this).inflate(R.layout.layout_order_header, recyclerView, false)
        if (TextUtils.isEmpty(SPUtil.getString(StaticValue.USER_ADDRESS, ""))) {
            address1 = header.findViewById<TextView>(R.id.tvAddress)
            address1.text = getString(R.string.add_address)
            address1.onClick {
                myStartActivityForResult(AddressListActivity::class.java, REQUEST_ONE)
            }

        } else {
            refreshHeader(header, null)
        }
        mAdapter.setHeaderView(header)
    }

    private fun refreshHeader(header: View, address: AddressBean?) {
        if (address != null) {
            header.findViewById<TextView>(R.id.tvName).text = address.receiver
            header.findViewById<TextView>(R.id.tvPhone).text = address.recvphone
            header.findViewById<TextView>(R.id.tvAddress).text = address.street
        }


//        header.findViewById<TextView>(R.id.tvName).text = SPUtil.getString(StaticValue.USER_NAME, "")
//        header.findViewById<TextView>(R.id.tvPhone).text = SPUtil.getString(StaticValue.USER_PHONE, "")
//        header.findViewById<TextView>(R.id.tvAddress).text = SPUtil.getString(StaticValue.USER_ADDRESS, "")

    }

    private var addressInfo: AddressBean? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode && requestCode == REQUEST_ONE) {
            addressInfo = data?.getSerializableExtra("address") as AddressBean
            refreshHeader(header, addressInfo)
        }
    }


    private fun setFooterView() {
        footer = LayoutInflater.from(this).inflate(R.layout.layout_order_footer, recyclerView, false)
        var price = intent.getStringExtra("price")
        footer.findViewById<TextView>(R.id.tvPrice).text = price
        mAdapter.setFooterView(footer)

    }


    private fun initListener() {
        ivBuy.onClick {
            if (addressInfo == null) {
                ToastUtil.showToast("请先添加收货地址")
            } else {
                doPlaceOrder()
            }

        }

    }

    private fun doPlaceOrder() {
        HttpMethods.createService().doPlaceOrder("do_placeorder", "", "", SPUtil.getString(StaticValue.USER_ID, ""), "")
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<CodeBean>>() {
                    override fun onNext(t: HttpResult<CodeBean>) {
                        super.onNext(t)

                        if (t.status == 0) {
                            myStartActivity(OrderResultActivity::class.java, true)
                        } else {
                            ToastUtil.showToast(t.info)
                        }
                    }
                })
//
    }

}