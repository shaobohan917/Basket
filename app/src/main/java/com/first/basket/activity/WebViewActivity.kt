package com.first.basket.activity

import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.first.basket.R
import com.first.basket.base.BaseActivity
import kotlinx.android.synthetic.main.activity_webview.*

/**
 * Created by hanshaobo on 10/10/2017.
 */
class WebViewActivity : BaseActivity() {
    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        initData()
    }

    private fun initData() {
        url = intent.getStringExtra("url")
        url = "http://www.baidu.com"
        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest?): Boolean {
                view.loadUrl(url)
                return true
            }
        }
    }
}