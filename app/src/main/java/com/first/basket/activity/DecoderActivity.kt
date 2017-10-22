package com.first.basket.activity

import android.content.Intent
import android.graphics.PointF
import android.os.Bundle
import android.widget.TextView
import com.dlazaro66.qrcodereaderview.QRCodeReaderView
import com.dlazaro66.qrcodereaderview.QRCodeReaderView.OnQRCodeReadListener
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.utils.LogUtils


/**
 * Created by hanshaobo on 21/10/2017.
 */
class DecoderActivity : BaseActivity(), OnQRCodeReadListener {

    private val resultTextView: TextView? = null
    private lateinit var qrCodeReaderView: QRCodeReaderView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decoder)

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

    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed in View
    override fun onQRCodeRead(text: String, points: Array<PointF>) {
        var intent = Intent(this@DecoderActivity, GoodsDetailActivity::class.java)
        intent.putExtra("ocr", text)
        myStartActivity(intent, true)

    }

    override fun onResume() {
        super.onResume()
        qrCodeReaderView!!.startCamera()
    }

    override fun onPause() {
        super.onPause()
        qrCodeReaderView!!.stopCamera()
    }
}