package com.first.basket.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.base.HttpResult
import com.first.basket.bean.LoginBean
import com.first.basket.common.CommonMethod
import com.first.basket.common.StaticValue
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.SPUtil
import com.first.basket.utils.ToastUtil
import com.first.basket.view.clicppictrue.ClipPictureActivity
import kotlinx.android.synthetic.main.activity_modify_nickname.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.io.File

/**
 * Created by hanshaobo on 15/10/2017.
 */
class ModifyAvatarActivity : BaseActivity() {
    private val CAPTURED_IMAGE_DIRECTORY = CommonMethod.getBasketDir() + "ocr/"
    private val FILE_NAME = "temp_cropped.jpg"

    //请求相机
    private val REQUEST_CAPTURE = 0x11
    //请求相册
    private val REQUEST_PICK = 0x12
    //请求截图
    private val REQUEST_CROP_PHOTO = 0x13

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_modify_nickname)

        goGallery()
    }

    //调用系统相册
    private fun goGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_PICK)
//        overridePendingTransition(R.anim.fade, R.anim.hold)
    }


    private fun goClicp(path: String) {
        val intent = Intent(this, ClipPictureActivity::class.java)
        intent.putExtra("path", path)
        startActivityForResult(intent, REQUEST_CROP_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_CANCELED) {
            setResult(0)
            finish()
            return
        }

        if (requestCode == REQUEST_PICK) {
            //相册，通过uri获取路径
            val uri = data.data
            val path = CommonMethod.getRealFilePath(this, uri)
            //压缩
//            CommonMethod.compressPicture(path, CAPTURED_IMAGE_DIRECTORY + FILE_NAME)
            goClicp(CAPTURED_IMAGE_DIRECTORY + FILE_NAME)
        } else if (requestCode == REQUEST_CROP_PHOTO) {
            //裁剪，获取bitmap
//            tvOcr.setText("正在识别中...")
//            bis = data.getByteArrayExtra("bitmap")
//            recognize(CAPTURED_IMAGE_DIRECTORY + FILE_NAME)
//            if (dialog != null) {
//                dialog.show()
//            }
            uploadAvatar()
        }
    }

    private fun uploadAvatar() {
        HttpMethods.createService().doUploadimage("do_uploadimage", SPUtil.getString(StaticValue.USER_ID, ""), "photo", File(""))
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<LoginBean>>() {
                    override fun onNext(t: HttpResult<LoginBean>) {
                        super.onNext(t)
                        SPUtil.setString(StaticValue.SP_LOGIN_USERNAME, t.result.data.username)
                        ToastUtil.showToast("修改成功")
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        ToastUtil.showToast("修改失败：" + e.message)
                    }

                    override fun onCompleted() {
                        super.onCompleted()
                        CommonMethod.hideKeyboard(etNickname)
                        setResult(Activity.RESULT_OK)
                        myFinish()
                    }
                })

    }
}