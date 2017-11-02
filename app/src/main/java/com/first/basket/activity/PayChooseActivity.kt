package com.first.basket.activity

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.alipay.sdk.app.PayTask
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.base.HttpResult
import com.first.basket.bean.AliBean
import com.first.basket.bean.WechatBean
import com.first.basket.common.CommonMethod
import com.first.basket.common.StaticValue
import com.first.basket.constants.Constants
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.Md5Util
import com.first.basket.utils.SPUtil
import com.first.basket.utils.ToastUtil
import com.first.basket.utils.alipay.PayResult
import com.tencent.mm.sdk.modelpay.PayReq
import com.tencent.mm.sdk.openapi.WXAPIFactory
import kotlinx.android.synthetic.main.activity_pay_choose.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.LinkedHashMap

/**
 * Created by hanshaobo on 31/10/2017.
 */
class PayChooseActivity : BaseActivity() {
    private val SDK_PAY_FLAG: Int = 1
    private var mPrice: Float = 0f
    private var mMap = HashMap<String, String>()
    private var isFirst = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_choose)
        initData()
        initListener()
    }

    private fun initData() {
        mPrice = intent.getFloatExtra("price", 0f)
        mMap = intent.getSerializableExtra("map") as HashMap<String, String>

        tvPrice.text = "¥ " + mPrice.toString()
        tvStatus.text = "待支付"
        tvTime.text = CommonMethod.getTime(true)
    }

    private fun initListener() {
        rlWechat.onClick { doPlaceOrderByThird(0) }
        rlAli.onClick { doPlaceOrderByThird(1) }
    }


    /**
     * 0微信 1支付宝
     */
    private fun doPlaceOrderByThird(type: Int) {
        if (type == 0) {
            //微信
            mMap.put("totalfee", (mPrice * 100).toInt().toString())
        } else {
            mMap.put("totalfee", mPrice.toString())
//            mMap.put("totalfee", "0.01")
        }

        if (type == 0) {
            HttpMethods.createService().doPayforwechat("do_payforwechat", mMap)
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
        } else {
            HttpMethods.createService().doPayforAli("do_payforalipay", mMap)
                    .compose(TransformUtils.defaultSchedulers())
                    .subscribe(object : HttpResultSubscriber<HttpResult<AliBean>>() {
                        override fun onNext(t: HttpResult<AliBean>) {
                            super.onNext(t)
                            if (t.status == 0) {
                                //去支付
                                aliPay(t.result.data)
                            } else {
                                ToastUtil.showToast(t.info + ":" + t.status)
                            }
                        }
                    })
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
                api.sendReq(payReq)
                isFirst = false
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


    private fun aliPay(data: AliBean.DataBean) {
        val orderInfo = data.preorder_result   // 订单信息

        val payRunnable = Runnable {
            val alipay = PayTask(this@PayChooseActivity)
            val result = alipay.payV2(orderInfo, true)

            val msg = Message()
            msg.what = SDK_PAY_FLAG
            msg.obj = result
            mHandler.sendMessage(msg)
        }
        // 必须异步调用
        val payThread = Thread(payRunnable)
        payThread.start()
    }

    private var mHandler = Handler(Handler.Callback { msg ->
        var payResult = PayResult(msg.obj as Map<String, String>)
        if ("9000" == payResult.resultStatus) {
            setResult(Activity.RESULT_OK)
        } else {
            setResult(RESULT_FAIL)
        }
        myFinish()
        false
    })

    override fun onResume() {
        super.onResume()
        if (!isFirst) {
            val code = SPUtil.getInt(StaticValue.ERROR_CODE_WECHAT, -2)
            if (code == 0) {
                //微信支付成功
                setResult(Activity.RESULT_OK)
            } else {
                setResult(RESULT_FAIL)
            }
            myFinish()
        }
    }
}