package com.first.basket.activity

import android.os.Bundle
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.base.HttpResult
import com.first.basket.bean.WechatBean
import com.first.basket.common.StaticValue
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.SPUtil
import com.first.basket.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_paychoose.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hanshaobo on 31/10/2017.
 */
class PayChooseActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paychoose)
        initData()
        initView()
    }

    private var mPrice: String = ""

    private fun initData() {
        mPrice = intent.getStringExtra("price")

        tvPrice.text = mPrice
//        tvStatus.text = status
//        tvTime.text = time


    }

    private fun initView() {
        rlAli.onClick { ToastUtil.showToast("支付宝") }
//        rlWechat.onClick { doPlaceOrderByWechat() }

    }

//    private fun doPlaceOrderByWechat() {
//        var productidString = StringBuilder()
//        var numString = StringBuilder()
//
//        for (i in 0 until mGoodsList.size) {
//            productidString.append(mGoodsList[i].productid).append("|")
//            numString.append(mGoodsList[i].amount).append("|")
//        }
//        val ps: String = productidString.toString().substring(0, productidString.length - 1)
//        val ns: String = numString.toString().substring(0, numString.length - 1)
//
//        var map = HashMap<String, String?>()
//        map.put("userid", SPUtil.getString(StaticValue.USER_ID, ""))
//        map.put("paytype", "APP")
//        map.put("productname", getString(R.string.app_name))
////        map.put("totalfee", "1")
//        map.put("totalfee", (mPrice * 100).toInt().toString())
//        map.put("productsid", ps)
//        map.put("productsNumber", ns)
//        map.put("addressid", addressInfo.addressid)
//
//        HttpMethods.createService().doPayforwechat("do_payforwechat", map)
//                .compose(TransformUtils.defaultSchedulers())
//                .subscribe(object : HttpResultSubscriber<HttpResult<WechatBean>>() {
//                    override fun onNext(t: HttpResult<WechatBean>) {
//                        super.onNext(t)
//                        if (t.status == 0) {
//                            //去支付
//                            wechatPay(t.result.data[0])
//                        } else {
//                            ToastUtil.showToast(t.info + ":" + t.status)
//                        }
//                    }
//                })
//    }
}