package com.first.basket.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.base.HttpResult
import com.first.basket.bean.LoginBean
import com.first.basket.common.StaticValue
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.ImageUtils
import com.first.basket.utils.SPUtil
import com.first.basket.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_personalinfo.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hanshaobo on 15/10/2017.
 */
class PersonalInfoActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personalinfo)
        initView()

    }

    private fun initView() {
        rlAvatar.onClick {
            startActivityForResult(Intent(this@PersonalInfoActivity, ModifyAvatarActivity::class.java),101)
        }

        rlNickname.onClick {
            startActivityForResult(Intent(this@PersonalInfoActivity,ModifyNicknameActivity::class.java),104)
        }

        rlShiming.onClick {
            startActivityForResult(Intent(this@PersonalInfoActivity,ShenfenActivity::class.java),104)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            getUserInfo()
        }
    }

    private fun getUserInfo() {
        HttpMethods.createService().getUserInfo("get_userinfo", SPUtil.getString(StaticValue.USER_ID, ""))
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<LoginBean>>() {
                    override fun onNext(t: HttpResult<LoginBean>) {
                        super.onNext(t)
                        var avatar = t.result.data.userphoto
                        ImageUtils.showImg(this@PersonalInfoActivity,avatar,ivAvatar)
                        ToastUtil.showToast("获取成功")
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        ToastUtil.showToast("获取失败：" + e.message)
                    }

                    override fun onCompleted() {
                        super.onCompleted()
                    }
                })

    }
}