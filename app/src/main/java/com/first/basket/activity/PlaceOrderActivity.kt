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
import com.first.basket.bean.WechatBean
import com.first.basket.common.CommonMethod
import com.first.basket.common.StaticValue
import com.first.basket.constants.Constants
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.LogUtils
import com.first.basket.utils.Md5Util
import com.first.basket.utils.SPUtil
import com.first.basket.utils.ToastUtil
import com.google.gson.GsonBuilder
import com.tencent.mm.sdk.modelpay.PayReq
import com.tencent.mm.sdk.openapi.WXAPIFactory
import kotlinx.android.synthetic.main.activity_place_order.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*
import java.util.Map
import kotlin.collections.HashMap


/**
 * Created by hanshaobo on 15/10/2017.
 */
class PlaceOrderActivity : BaseActivity() {
    private var mPrice: Float = 0f
    private var mGoodsList = ArrayList<ProductBean>()

    private lateinit var header: View
    private lateinit var footer: View
    private lateinit var addressInfo: AddressBean

    private lateinit var mAdapter: PlaceOrderAdapter

    private var isWechatRepeat: Boolean = false     //微信支付失败，重新支付

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_order)
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
        mAdapter = PlaceOrderAdapter(this@PlaceOrderActivity, mGoodsList)
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
        footer.findViewById<TextView>(R.id.tvCount).text = getString(R.string.product_count, BaseApplication.getInstance().productsCount.toString())
        mAdapter.addFooterView(footer)

        tvTotalPrice.text = getString(R.string.total_price, mPrice.toString())
    }


    private fun initListener() {
        btBuy.onClick {
            doPlaceOrderByWechat()
        }
    }

    private fun wechatPay(dataBean: WechatBean.DataBean) {
        val appid = dataBean?.appid
        val mchid = dataBean?.mchid
        val noncestr = dataBean?.noncestr
        val prepayid = dataBean?.prepayid

        val api = WXAPIFactory.createWXAPI(this, Constants.WECHAT_APP_ID)
        api.registerApp(Constants.WECHAT_APP_ID)
        if (api != null) {
            if (CommonMethod.isPkgInstalled(this, Constants.WECHAT_PACKAGE)) {
                val payReq = PayReq()
                payReq.appId = appid
                payReq.nonceStr = Md5Util.getMd5Value(noncestr)
                payReq.packageValue = "Sign=WXPay"
                payReq.partnerId = mchid
                payReq.prepayId = prepayid
                payReq.timeStamp = (System.currentTimeMillis() / 1000).toString() + ""

                val map = LinkedHashMap<String, String>()
                map.put("appid", payReq.appId)
                map.put("noncestr", payReq.nonceStr)
                map.put("package", payReq.packageValue)
                map.put("partnerid", payReq.partnerId)
                map.put("prepayid", payReq.prepayId)
                map.put("timestamp", payReq.timeStamp)
                //生成sign
                payReq.sign = genAppSign(map)


//                order = data.out_trade_no

                api.sendReq(payReq)
            } else {
                ToastUtil.showToast(getString(R.string.not_install, "微信"))
            }
        }
    }

    private fun genAppSign(map: LinkedHashMap<String, String>): String {
        val sb = StringBuilder()
        val iterator = map.entries.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next() as Map.Entry<*, *>
            val key = entry.key
            val value = entry.value
            sb.append(key).append("=").append(value).append("&")
        }
        sb.append("key=")
        sb.append(Constants.WECHAT_KEY)
        return Md5Util.getMd5Value(sb.toString()).toUpperCase()
    }

    private fun doPlaceOrderByWechat() {
        var productidString = StringBuilder()
        var numString = StringBuilder()

        for (i in 0 until mGoodsList.size) {
            productidString.append(mGoodsList[i].productid).append("|")
            numString.append(mGoodsList[i].amount).append("|")
        }
        val ps: String = productidString.toString().substring(0, productidString.length - 1)
        val ns: String = numString.toString().substring(0, numString.length - 1)

        var map = HashMap<String, String?>()
        map.put("userid", SPUtil.getString(StaticValue.USER_ID, ""))
        map.put("paytype", "APP")
        map.put("productname", getString(R.string.app_name))
//        map.put("totalfee", "1")
        map.put("totalfee", (mPrice * 100).toInt().toString())
        map.put("productsid", ps)
        map.put("productsNumber", ns)
        map.put("addressid", addressInfo.addressid)

        HttpMethods.createService().doPayforwechat("do_payforwechat", map)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<WechatBean>>() {
                    override fun onNext(t: HttpResult<WechatBean>) {
                        super.onNext(t)
                        if (t.status == 0) {
                            //去支付
                            wechatPay(t.result.data[0])
                        } else {
                            ToastUtil.showToast(t.info + ":" + t.status)
                        }
                    }
                })
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
            var product = BaseApplication.getInstance().productsList.remove(mGoodsList[i])
            LogUtils.d("删除：" + product)
        }

    }

    override fun onResume() {
        super.onResume()
        val code = SPUtil.getInt(StaticValue.ERROR_CODE_WECHAT, -2)
        if (!isWechatRepeat) {
            if (code == 0) {
                deleteProducts()
                myStartActivity(OrderResultActivity::class.java, true)
            } else {
//                ToastUtil.showToast("支付失败")
            }
        }

    }
}