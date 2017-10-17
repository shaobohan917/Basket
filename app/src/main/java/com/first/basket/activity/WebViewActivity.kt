package com.first.basket.activity

import android.annotation.SuppressLint
import android.os.Bundle
import com.first.basket.R
import com.first.basket.base.BaseActivity
import kotlinx.android.synthetic.main.activity_webview.*
import android.net.http.SslError
import android.view.View
import android.webkit.*


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

    @SuppressLint("SetJavaScriptEnabled")
    private fun initData() {
        url = intent.getStringExtra("url")

        var webSettings = webview.settings

        // 解决某些网址显示异常的问题，4种属性需同时设置
        webview.webViewClient = MyWebViewClient()

        webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress == 100) {
                    pb.visibility = View.INVISIBLE;
                } else {
                    if (View.INVISIBLE == pb.getVisibility()) {
                        pb.visibility = View.VISIBLE;
                    }
                    pb.progress = newProgress;
                }
                super.onProgressChanged(view, newProgress);
            }

        }
        webSettings.domStorageEnabled = true;
        webSettings.javaScriptEnabled = true;

        // 设置自适应屏幕
        webSettings.useWideViewPort = true;
        webSettings.loadWithOverviewMode = true;

        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE;

        webview.loadUrl(url)
    }

}


internal class MyWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        view.loadUrl(url)
        return true
    }

    override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
        handler.proceed()
    }
}