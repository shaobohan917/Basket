package com.first.basket.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.first.basket.R
import com.first.basket.adapter.PlaceOrderAdapter
import com.first.basket.app.BaseApplication
import com.first.basket.base.BaseActivity
import com.first.basket.bean.AddressBean
import com.first.basket.bean.ProductBean
import com.first.basket.common.CommonMethod
import com.first.basket.common.StaticValue
import com.first.basket.utils.LogUtils
import com.first.basket.utils.SPUtil
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_pay_order.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import kotlin.collections.HashMap


/**
 * Created by hanshaobo on 15/10/2017.
 */
class PayOrderActivity : BaseActivity() {
    private var mPrice: Float = 0f
    private var mCount: Int = 0
    private var mGoodsList = ArrayList<ProductBean>()

    private lateinit var header: View
    private lateinit var footer: View
    private lateinit var addressInfo: AddressBean

    private lateinit var mAdapter: PlaceOrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_order)
        SPUtil.setInt(StaticValue.ERROR_CODE_WECHAT, -3)       //进入支付页面时，即给微信错误码设置一个初始值
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
    }


    private fun initData() {
        setHeader()
        var mAllCheckProducts = BaseApplication.getInstance().isCheckProducts
        if (!CommonMethod.isTrue(addressInfo.issqcs)) {
            mAllCheckProducts.filter { (it.channelid == "1") }.forEach {
                mAllCheckProducts.remove(it)
            }
        }
        mGoodsList.addAll(mAllCheckProducts)

        for (productBean in mGoodsList) {
            mCount += productBean.amount
        }
        mAdapter = PlaceOrderAdapter(this@PayOrderActivity, mGoodsList)
        recyclerView.adapter = mAdapter

        mAdapter.addHeaderView(header)

        mAdapter.notifyDataSetChanged()

        setFooter()
    }

    private fun setHeader() {
        header = LayoutInflater.from(this).inflate(R.layout.layout_order_header, recyclerView, false)
        var str = SPUtil.getString(StaticValue.DEFAULT_ADDRESS, "")
        addressInfo = GsonBuilder().create().fromJson(str, AddressBean::class.java)
        header.findViewById<TextView>(R.id.tvName).text = addressInfo.receiver
        header.findViewById<TextView>(R.id.tvPhone).text = addressInfo.recvphone
        header.findViewById<TextView>(R.id.tvAddress).text = addressInfo.street
    }

    private fun setFooter() {
        footer = LayoutInflater.from(this).inflate(R.layout.layout_order_footer, recyclerView, false)
        mPrice = intent.getFloatExtra("price", 0f)
        footer.findViewById<TextView>(R.id.tvPrice).text = getString(R.string.total_price, mPrice.toString())
        footer.findViewById<TextView>(R.id.tvCount).text = getString(R.string.product_count, mCount.toString())
        footer.findViewById<TextView>(R.id.tvTotal).text = "¥ " + mPrice
        mAdapter.addFooterView(footer)

        tvTotalPrice.text = getString(R.string.total_price, mPrice.toString())
    }


    private fun initListener() {
        btBuy.onClick {
            var intent = Intent(this@PayOrderActivity, PayChooseActivity::class.java)
            intent.putExtra("price", mPrice)
            intent.putExtra("map", generateParams())
            myStartActivityForResult(intent, REQUEST_ONE)
        }
    }

    /**
     * 生成支付所需参数
     */
    private fun generateParams(): HashMap<String, String> {
        var productidString = StringBuilder()
        var numString = StringBuilder()

        for (i in 0 until mGoodsList.size) {
            productidString.append(mGoodsList[i].productid).append("|")
            numString.append(mGoodsList[i].amount).append("|")
        }
        val ps: String = productidString.toString().substring(0, productidString.length - 1)
        val ns: String = numString.toString().substring(0, numString.length - 1)

        var map = HashMap<String, String>()
        map.put("userid", SPUtil.getString(StaticValue.USER_ID, ""))
        map.put("paytype", "APP")
        map.put("productname", getString(R.string.app_name))
        map.put("productsid", ps)
        map.put("productsNumber", ns)
        map.put("addressid", addressInfo.addressid)
        return map
    }

    private fun deleteProducts() {
        for (i in 0 until mGoodsList.size) {
            var product = BaseApplication.getInstance().productsList.remove(mGoodsList[i])
            LogUtils.d("删除：" + product)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode) {
            deleteProducts()
            var intent = Intent(this@PayOrderActivity, PayResultActivity::class.java)
            intent.putExtra("success", true)
            myStartActivity(intent, true)
        } else if (RESULT_FAIL == resultCode) {
            deleteProducts()
            var intent = Intent(this@PayOrderActivity, PayResultActivity::class.java)
            intent.putExtra("success", false)
            myStartActivity(intent, true)
        }
    }
}