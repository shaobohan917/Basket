package com.first.basket.activity

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.*
import com.first.basket.R
import com.first.basket.base.BaseActivity
import kotlinx.android.synthetic.main.activity_webview.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by hanshaobo on 10/10/2017.
 */
class WebViewActivity : BaseActivity() {
    private var url: String? = ""
    private var mTitle: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        initData()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initData() {
        url = intent.getStringExtra("url")
        if (TextUtils.isEmpty(url)) myFinish()
        mTitle = intent.getStringExtra("title")

        titleView.setTitle(mTitle)

        var webSettings = webview.settings

        // 解决某些网址显示异常的问题，4种属性需同时设置
        webview.webViewClient = MyWebViewClient()

        webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress == 100) {
                    pb.visibility = View.INVISIBLE
                    showBtGo()

                } else {
                    if (View.INVISIBLE == pb.visibility) {
                        pb.visibility = View.VISIBLE
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

    private fun showBtGo() {
        if ("大病保障" == mTitle) {
            bt_go.visibility = View.VISIBLE
            bt_go.text = getString(R.string.agreenYibao)
            bt_go.background = resources.getDrawable(R.drawable.corner_green)
        } else if ("企业福利" == mTitle) {
            bt_go.visibility = View.VISIBLE
            bt_go.text = getString(R.string.aggeenLove)
            bt_go.background = resources.getDrawable(R.drawable.corner_pink)
        }
        bt_go.onClick {
            MainActivity.getInstance1().goClassify(3)
            myFinish()
        }
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