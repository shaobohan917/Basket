package com.first.basket.activity

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.bean.RecommendBean
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.ScreenUtils
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.padding
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.wrapContent

/**
* Created by hanshaobo on 12/09/2017.
*/
class SearchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        getSuspect()
    }

    private fun getSuspect() {
        HttpMethods.createService().getRecommend("get_recommend")
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<RecommendBean>() {
                    override fun onNext(t: RecommendBean) {
                        super.onNext(t)
                        setData(t.result.data)
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                    }
                })
    }


    private fun setData(data: List<RecommendBean.ResultBean.DataBean>) {
        for (i in 0 until data.size) {
            val p = ScreenUtils.dip2px(this, 10f)
            val tv = TextView(this)
            val params = LinearLayout.LayoutParams(wrapContent, wrapContent)
            params.setMargins(0, 0, p, p)
            tv.padding = p
            tv.setBackgroundColor(resources.getColor(R.color.grayF4))
            tv.layoutParams = params

            tv.text = data[i].productname
            tv.onClick {
                var intent = Intent(this@SearchActivity, GoodsDetailActivity::class.java)
                intent.putExtra("id", data[i].productid)
                startActivity(intent)
            }

            llRecommend.addView(tv)
        }
    }
}