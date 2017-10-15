package com.first.basket.activity

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.base.HttpResult
import com.first.basket.bean.CodeBean
import com.first.basket.bean.LoginBean
import com.first.basket.common.CommonMethod
import com.first.basket.common.StaticValue
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.CountDownUtil
import com.first.basket.utils.LogUtils
import com.first.basket.utils.SPUtil
import com.first.basket.utils.ToastUtil
import com.first.basket.view.TitleView
import kotlinx.android.synthetic.main.activity_login_pwd.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hanshaobo on 15/10/2017.
 */
class LoginPwdActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {


    }

    private lateinit var titleView: TitleView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_pwd)
        initView()
        initData()
        initListener()

    }

    private fun initListener() {
        btSendCode.onClick {
            if (!CommonMethod.isMobileNO(etPhone.getText().toString())) {
                ToastUtil.showToast(this@LoginPwdActivity, "请输入正确的手机号")
                return@onClick
            }
            getLoginVerifyCode(etPhone.getText().toString())
        }

        btLogin.onClick {
            doLogin(etPhone.text.toString(), etPassword.text.toString(), "")
        }
    }

    private fun initData() {

    }

    private fun initView() {
//        titleView = findViewById(R.id.titleView)
//        titleView.setBackgroundColor(resources.getColor(R.color.colorLogin))
    }


    private fun getLoginVerifyCode(phonenumber: String) {

        HttpMethods.createService()
                .getCode("get_code", phonenumber)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<CodeBean>>() {
                    override fun onNext(t: HttpResult<CodeBean>) {
                        super.onNext(t)
                        countDown()
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                    }
                })
    }

    private fun doLogin(phonenumber: String, code: String, password: String) {
        HttpMethods.createService()
                .doLogin("do_login", phonenumber, code, password)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<LoginBean>>() {
                    override fun onNext(t: HttpResult<LoginBean>) {
                        super.onNext(t)
                        LogUtils.d(t.result.data.phone)
                        ToastUtil.showToast(this@LoginPwdActivity, "登陆成功")
                        SPUtil.setBoolean(StaticValue.SP_LOGIN_STATUS, true)

                        SPUtil.setString(StaticValue.SP_LOGIN_PHONE, t.result.data.phone)
                        SPUtil.setString(StaticValue.USER_ID, t.result.data.userid)
                        setResult(Activity.RESULT_OK)
                        Handler().postDelayed({ finish() }, 1000)

                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        ToastUtil.showToast(this@LoginPwdActivity, e.message.toString())
                    }
                })
    }

    private fun countDown() {
        setButtonStatus(false)
        CountDownUtil.countDown(120, object : CountDownUtil.onCountDownListener {
            override fun onCountDownNext(integer: Int?) {
                btSendCode.text = integer.toString() + "秒后重新发送"
            }

            override fun onCountDownError(e: Throwable?) {

            }

            override fun onCountDownComplete() {
                countDownComplete()
            }
//            }
        })
    }

    private fun setButtonStatus(isComplete: Boolean) {
        if (isComplete) {
            btSendCode.background = resources.getDrawable(R.color.colorLogin)
            btSendCode.setTextColor(resources.getColor(R.color.white))
            btSendCode.isClickable = true
            btSendCode.text = "获取验证码"
            btSendCode.setOnClickListener(this)
        } else {
            btSendCode.background = resources.getDrawable(R.color.text_bg)
            btSendCode.setTextColor(resources.getColor(R.color.gray99))
            btSendCode.isClickable = false
            btSendCode.setOnClickListener(null)
        }
    }

    private fun countDownComplete() {
        setButtonStatus(true)
    }
}