package com.first.basket.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView
import com.coding.tag_layouter.AutoLayoutContainer
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.bean.RecommendBean
import com.first.basket.common.StaticValue
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.LogUtils
import com.first.basket.utils.SPUtil
import com.first.basket.utils.ScreenUtils
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.padding
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.wrapContent
import java.util.*
import kotlin.collections.HashSet

/**
 * Created by hanshaobo on 12/09/2017.
 */
class SearchActivity : BaseActivity() {
    private lateinit var alc_vertical: AutoLayoutContainer
    private var listSet = HashSet<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        getSuspect()
        initView()
        initData()
        initListener()
    }

    private fun initData() {
        listSet = SPUtil.getStringSet(StaticValue.SEARCH, listSet) as HashSet<String>
        LogUtils.d("listset.size;" + listSet.size)

    }

    private fun initListener() {
        etSearch.setOnEditorActionListener { p0, p1, p2 ->
            if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                LogUtils.d("sousuo:" + p0.text)
                var intent = Intent(this@SearchActivity,SearchListActivity::class.java)
                intent.putExtra("search",p0.text)
                myStartActivity(intent)
            }
            false
        }
    }

    private fun initView() {
        alc_vertical = findViewById(R.id.alc_vertical)

    }

    private fun getSuspect() {
        HttpMethods.createService().getRecommend("get_recommend")
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<RecommendBean>() {
                    override fun onNext(t: RecommendBean) {
                        super.onNext(t)
                        setData(t.result.data)
                    }
                })
    }


    private fun setData(data: List<RecommendBean.ResultBean.DataBean>) {
        var strs = ArrayList<String>()
        var sb = StringBuilder()

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
                myStartActivity(intent, true)
            }
//            llRecommend.addView(tv)

            strs.add(data[i].productname)
            sb.append(data[i].productname + ",")
        }
        var sss = strs.toString()
        sss = sss.substring(1, sss.length - 1)
        alc_vertical.addTag<TextView>(R.layout.layout_tag, strs[0], strs[1], strs[2], strs[3], strs[4], strs[5])

        alc_vertical.setOnSelectListener(object : AutoLayoutContainer.OnSelectListener {
            override fun onSelect(tag: View?, position: Int) {
                var str = strs[position]
                listSet.add(str)
                SPUtil.setStringSet(StaticValue.SEARCH, listSet)
                LogUtils.d("xuanzhong:" + strs[position])
                var intent = Intent(this@SearchActivity,SearchListActivity::class.java)
                intent.putExtra("search",str)
                startActivity(intent)
            }
        })
    }
}