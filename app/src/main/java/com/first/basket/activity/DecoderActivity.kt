package com.first.basket.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PointF
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.dlazaro66.qrcodereaderview.QRCodeReaderView
import com.dlazaro66.qrcodereaderview.QRCodeReaderView.OnQRCodeReadListener
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.utils.ToastUtil
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import java.lang.Exception


/**
 * Created by hanshaobo on 21/10/2017.
 */
class DecoderActivity : BaseActivity(), OnQRCodeReadListener {

    private lateinit var qrCodeReaderView: QRCodeReaderView
    private val MY_PERMISSIONS_REQUEST_CAMERA = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decoder)
//        test123()
        initView()
    }

    private fun test123() {
        AndPermission.with(this@DecoderActivity)
                .requestCode(100)
                .permission(Permission.LOCATION)
                .rationale { requestCode, rationale -> ToastUtil.showToast("request") }.callback(ToastUtil.showToast("result"))
                .start()

    }

    private fun checkPermissionOfCamera() {
        if (ContextCompat.checkSelfPermission(this@DecoderActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@DecoderActivity, Manifest.permission.CAMERA)) {
                ToastUtil.showToast("提示信息")
            } else {
                ActivityCompat.requestPermissions(this@DecoderActivity, arrayOf(Manifest.permission.CAMERA), MY_PERMISSIONS_REQUEST_CAMERA)
            }
        } else {
            try {
                qrCodeReaderView!!.startCamera()
            } catch (e: Exception) {
                ToastUtil.showToast("请在系统设置中打开相机权限")
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CAMERA ->
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ToastUtil.showToast("权限已获取")
                } else {
                    ToastUtil.showToast("权限未获取")
                }
        }

    }

    private fun initView() {
        qrCodeReaderView = findViewById(R.id.qrdecoderview)
        qrCodeReaderView.setOnQRCodeReadListener(this)

        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true)

        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(2000L)

        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true)

        // Use this function to set front camera preview
        qrCodeReaderView.setFrontCamera()

        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera()
    }

    override fun onQRCodeRead(text: String, points: Array<PointF>) {
        var intent = Intent(this@DecoderActivity, GoodsDetailActivity::class.java)
        intent.putExtra("ocr", text)
        myStartActivity(intent, true)
    }

    override fun onResume() {
        super.onResume()
        checkPermissionOfCamera()
    }

    override fun onPause() {
        super.onPause()
        qrCodeReaderView!!.stopCamera()
    }

}