package com.first.basket.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.base.HttpResult
import com.first.basket.bean.DeliverBean
import com.first.basket.common.CommonMethod
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import kotlinx.android.synthetic.main.activity_range.*
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.wrapContent

/**
 * Created by hanshaobo on 15/10/2017.
 */
class RangeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_range)

        initData()
    }

    private fun initData() {
        HttpMethods.createService().getDeliverare("get_deliveryarea")
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<DeliverBean>>() {
                    override fun onNext(t: HttpResult<DeliverBean>) {
                        super.onNext(t)
                        setData(t.result.data)
                    }

                    override fun onCompleted() {
                        super.onCompleted()
                    }
                })


    }

    private fun setData(result: DeliverBean.DataBean) {
        var params = LinearLayout.LayoutParams(matchParent, wrapContent)
        params.setMargins(0,CommonMethod.dip2px(this,10f),0,0)
        for (i in 0 until result.sqcs.size) {
            var tv = TextView(this)
            tv.text = result.sqcs[i].subdistrict
            tv.setTextColor(resources.getColor(R.color.gray33))
            tv.layoutParams = params
            llSqContainer.addView(tv)
        }
        for (i in 0 until result.yhcs.size) {
            var tv = TextView(this)
            tv.text = result.yhcs[i].subdistrict
            tv.setTextColor(resources.getColor(R.color.gray33))
            tv.layoutParams = params
            llQgContainer.addView(tv)
        }
        if(result.shcs==null){
            tvSHCS.visibility = View.GONE
        }else{
            tvSHCS.visibility = View.VISIBLE
            for (i in 0 until result.shcs.size) {
                var tv = TextView(this)
                tv.text = result.shcs[i].subdistrict
                tv.setTextColor(resources.getColor(R.color.gray33))
                tv.layoutParams = params
                llShContainer.addView(tv)
            }
        }
    }

}