package com.first.basket.activity

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
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
import com.first.basket.utils.*
import com.first.basket.view.TitleView
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hanshaobo on 15/10/2017.
 */
class RegisterActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initView()
        initData()
        initListener()

    }

    private fun initView() {
        var titleView = findViewById<TitleView>(R.id.titleView)
        var title = intent.getStringExtra("title")
        if (TextUtils.isEmpty(title)) {
            titleView.setTitle("注册")
            btLogin.text = "注册"
        } else {
            titleView.setTitle("修改密码")
            btLogin.text = "修改密码"
        }
    }

    private fun initData() {

    }


    private fun initListener() {
        btSendCode.onClick {
            if (!CommonMethod.isMobile(etPhone.getText().toString())) {
                ToastUtil.showToast(this@RegisterActivity, "请输入正确的手机号")
                return@onClick
            }
            getLoginVerifyCode(etPhone.getText().toString())
        }

        btLogin.onClick {
            if (TextUtils.isEmpty(title)) {
                doRegister(etPhone.text.toString(), etCode.text.toString(), Md5Util.getMd5Value(etPassword.text.toString()))
            } else {

                changePassword(etPhone.text.toString(), etCode.text.toString(), Md5Util.getMd5Value(etPassword.text.toString()))
            }
        }
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

    private fun doRegister(phonenumber: String, code: String, password: String) {
        HttpMethods.createService()
                .doRegister("do_registered", phonenumber, code, password)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<LoginBean>>() {
                    override fun onNext(t: HttpResult<LoginBean>) {
                        super.onNext(t)
                        if (t.status == 0) {
                            LogUtils.d("注册成功" + t.result.data.phone)
                            ToastUtil.showToast(this@RegisterActivity, "注册成功")
                            SPUtil.setBoolean(StaticValue.SP_LOGIN_STATUS, true)

                            SPUtil.setString(StaticValue.SP_LOGIN_PHONE, t.result.data.phone)
                            SPUtil.setString(StaticValue.USER_ID, t.result.data.userid)
                            setResult(Activity.RESULT_OK)
                            CommonMethod.hideKeyboard(etPassword)
                            Handler().postDelayed({ finish() }, 1000)
                        } else {
                            ToastUtil.showToast(this@RegisterActivity, t.info)
                        }
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        ToastUtil.showToast(this@RegisterActivity, e.message.toString())
                    }
                })
    }

    private fun changePassword(phonenumber: String, code: String, password: String) {
        HttpMethods.createService()
                .doRegister("do_changepassword", phonenumber, code, password)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<LoginBean>>() {
                    override fun onNext(t: HttpResult<LoginBean>) {
                        super.onNext(t)
                        if (t.status == 0) {
                            ToastUtil.showToast(this@RegisterActivity, "修改成功")
                            setResult(Activity.RESULT_OK)
                            CommonMethod.hideKeyboard(etPassword)
                            Handler().postDelayed({ finish() }, 1000)
                        } else {
                            ToastUtil.showToast(this@RegisterActivity, t.info)
                        }
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        ToastUtil.showToast(this@RegisterActivity, e.message.toString())
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
            btSendCode.background = resources.getDrawable(R.color.colorMain)
            btSendCode.setTextColor(resources.getColor(R.color.white))
            btSendCode.isClickable = true
            btSendCode.text = "获取验证码"
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