package com.first.basket.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.first.basket.R
import com.first.basket.activity.LoginActivity
import com.first.basket.activity.OrderListActivity
import com.first.basket.activity.SettingActivity
import com.first.basket.common.StaticValue
import com.first.basket.utils.LogUtils
import com.first.basket.utils.SPUtil
import kotlinx.android.synthetic.main.fragment_mine.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hanshaobo on 30/08/2017.
 */
class MineFragment : BaseFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        setLoginStatus()

        rlOrder.onClick {
            //            startActivity(Intent(activity, OrderListActivity::class.java))
        }

        ivAva.onClick {
            tvLoginStatus.performClick()
        }

        tvLoginStatus.onClick {
            if (!SPUtil.getBoolean(StaticValue.SP_LOGIN_STATUS, false)) {
                var intent = Intent(activity, LoginActivity::class.java)
                startActivityForResult(intent, 102)
            } else {
                LogUtils.d("已登录")
            }
        }

        aimvSetting.setOnClickListener {
            var intent = Intent(activity, SettingActivity::class.java)
            startActivityForResult(intent, 103)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && (requestCode == 102 || requestCode == 103)) {
            setLoginStatus()
        }
    }

    fun setLoginStatus() {
        if (SPUtil.getBoolean(StaticValue.SP_LOGIN_STATUS, false)) {
            val phone = SPUtil.getString(StaticValue.SP_LOGIN_PHONE, "")
            tvLoginStatus.setText(phone)
        } else {
            tvLoginStatus.setText("登录/注册")
        }
    }
}