package com.first.basket.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_shenfen.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hanshaobo on 15/10/2017.
 */
class ShenfenActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shenfen)

        btNext.onClick {
            var name = etName.text.toString()
            var idCard = etIdCard.text.toString()
            if (TextUtils.isEmpty(name)) {
                ToastUtil.showToast("请输入真实姓名")
                return@onClick
            }
            if (TextUtils.isEmpty(idCard)) {
                ToastUtil.showToast("请输入身份证号")
                return@onClick
            }
            if (idCard.length != 18) {
                ToastUtil.showToast("请输入正确的身份证号")
                return@onClick
            }
            startActivity(Intent(this@ShenfenActivity, CardPhotoActivity::class.java))
        }
    }
}