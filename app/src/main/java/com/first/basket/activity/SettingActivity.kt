package com.first.basket.activity

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.common.StaticValue
import com.first.basket.utils.SPUtil
import com.first.basket.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hanshaobo on 15/10/2017.
 */
class SettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initView()
    }

    private fun initView() {
        if (SPUtil.getBoolean(StaticValue.SP_LOGIN_STATUS, false)) {
            loginOut.setBackgroundColor(resources.getColor(R.color.colorLogin))
            loginOut.onClick {
                SPUtil.setBoolean(StaticValue.SP_LOGIN_STATUS, false)
                SPUtil.setString(StaticValue.SP_LOGIN_PHONE, "")
                ToastUtil.showToast(this@SettingActivity, "退出登录")
                setResult(Activity.RESULT_OK)
                Handler().postDelayed({ finish() }, 1000)
            }
        } else {
            loginOut.setBackgroundColor(resources.getColor(R.color.white))
            loginOut.onClick {   }
        }
    }
}