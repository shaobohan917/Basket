package com.first.basket.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.common.StaticValue
import com.first.basket.rxjava.RxjavaUtil
import com.first.basket.rxjava.UITask
import com.first.basket.utils.SPUtil
import com.first.basket.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.concurrent.TimeUnit

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
            loginOut.setTextColor(resources.getColor(R.color.black))
            loginOut.onClick {
                SPUtil.setBoolean(StaticValue.SP_LOGIN_STATUS, false)
                SPUtil.setString(StaticValue.SP_LOGIN_PHONE, "")
                setResult(Activity.RESULT_OK)
                RxjavaUtil.doInUIThreadDelay(object : UITask<Any>() {
                    override fun doInUIThread() {
                        ToastUtil.showToast(this@SettingActivity, "退出登录")
                        finish()
                    }

                }, 500, TimeUnit.MILLISECONDS)
            }
        } else {
            loginOut.setTextColor(resources.getColor(R.color.gray99))
            loginOut.onClick { }
        }

        rlModifyPwd.onClick {
            var intent = Intent(this@SettingActivity, RegisterActivity::class.java)
            intent.putExtra("title","修改密码")
            startActivity(intent)
        }
    }
}