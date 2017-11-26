package com.first.basket.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.first.basket.R
import com.first.basket.base.HttpResult
import com.first.basket.bean.ActiveBean
import com.first.basket.http.ApiService
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import kotlinx.android.synthetic.main.fragment_active.*
import android.view.KeyEvent.KEYCODE_BACK



/**
 * Created by hanshaobo on 30/08/2017.
 */
class ActiveFragment : BaseFragment() {
    private lateinit var url:String

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_active, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        HttpMethods.createService()
                .getActivePage("get_activitypage")
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<ActiveBean>>() {
                    override fun onNext(t: HttpResult<ActiveBean>) {
                        super.onNext(t)
                        url = t.result.data.activityurl
                        initView()
                    }
                })
    }


    private fun initView() {
        var settings = webview.settings
        settings.javaScriptEnabled = true
        settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webview.webViewClient = MyWebViewClient()
        webview.loadUrl(url)
    }

    class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view!!.loadUrl(url)
            return true
        }
    }

    fun setBack():Boolean {
        if (webview.canGoBack()) {
            webview.goBack()// 返回前一个页面
            return true
        }
        return false
    }
}