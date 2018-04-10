package com.first.basket.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.base.HttpResult
import com.first.basket.bean.LoginBean
import com.first.basket.common.CommonMethod
import com.first.basket.common.StaticValue
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.ImageUtils
import com.first.basket.utils.SPUtil
import com.first.basket.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_cardphoto.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.io.File

/**
 * Created by hanshaobo on 15/10/2017.
 */
class CardPhotoActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cardphoto)

        initView()
    }

    private fun initView() {
        ivZheng.onClick {
            var intent = Intent(this@CardPhotoActivity, ModifyAvatarActivity::class.java)
            intent.putExtra("fromCard",1)
            startActivityForResult(intent,101)
        }
        ivFan.onClick {
            var intent = Intent(this@CardPhotoActivity, ModifyAvatarActivity::class.java)
            intent.putExtra("fromCard",1)
            startActivityForResult(intent,102)
        }

        btRenzheng.onClick {

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            var path = CommonMethod.getBasketDir() + "temp_cropped.jpg"
            if(requestCode==101){
                ImageUtils.showImgFile(this@CardPhotoActivity,path,ivZheng)
            }else{
                ImageUtils.showImgFile(this@CardPhotoActivity,path,ivFan)
            }
        }
    }

    private fun uploadAvatar() {
//        HttpMethods.createService().doUploadimage("do_uploadimage", SPUtil.getString(StaticValue.USER_ID, ""), "photo", File(CommonMethod.getBasketDir()+ "temp_cropped.jpg"))
//                .compose(TransformUtils.defaultSchedulers())
//                .subscribe(object : HttpResultSubscriber<HttpResult<LoginBean>>() {
//                    override fun onNext(t: HttpResult<LoginBean>) {
//                        super.onNext(t)
//                        SPUtil.setString(StaticValue.SP_LOGIN_USERNAME, t.result.data.username)
//                        ToastUtil.showToast("修改成功")
//                    }
//
//                    override fun onError(e: Throwable) {
//                        super.onError(e)
//                        ToastUtil.showToast("修改失败：" + e.message)
//                    }
//
//                    override fun onCompleted() {
//                        super.onCompleted()
//                        setResult(Activity.RESULT_OK)
//                        myFinish()
//                    }
//                })

    }
}