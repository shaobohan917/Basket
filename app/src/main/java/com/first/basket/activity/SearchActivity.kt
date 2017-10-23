package com.first.basket.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
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
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.item_et_search.*

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

    private fun initView() {
        alc_vertical = findViewById(R.id.alc_vertical)
    }

    override fun onResume() {
        super.onResume()
        listSet = SPUtil.getStringSet(StaticValue.SEARCH, listSet) as HashSet<String>
        LogUtils.d("listset.size;" + listSet.size)
        if (listSet.size > 0) {
            alc_vertical_recent.addTag<TextView>(R.layout.layout_tag, listSet.elementAt(0))
            alc_vertical_recent.setOnSelectListener { tag, position ->
                var intent = Intent(this@SearchActivity, SearchListActivity::class.java)
                intent.putExtra("search", listSet.elementAt(0))
                startActivity(intent)
            }
        }
    }

    private fun initData() {

    }

    private fun initListener() {
        etSearch.setOnEditorActionListener { p0, p1, p2 ->
            if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                listSet.add(p0.text.toString())
                SPUtil.setStringSet(StaticValue.SEARCH, listSet)
                var intent = Intent(this@SearchActivity, SearchListActivity::class.java)
                intent.putExtra("search", p0.text.toString())
                myStartActivity(intent)
            }
            false
        }
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
        val strs = (0 until data.size).map { data[it].productname }
        alc_vertical.addTag<TextView>(R.layout.layout_tag, strs[0], strs[1], strs[2], strs[3], strs[4], strs[5])

        alc_vertical.setOnSelectListener(object : AutoLayoutContainer.OnSelectListener {
            override fun onSelect(tag: View?, position: Int) {

                LogUtils.d("sele:" + position)
            }

        })

//        alc_vertical.setOnSelectListener { tag, position ->
//            LogUtils.d("pos:"+position)
//            var str = strs[position]
//            listSet.add(str)
//            SPUtil.setStringSet(StaticValue.SEARCH, listSet)
//            LogUtils.d("xuanzhong:" + strs[position])
//            var intent = Intent(this@SearchActivity, SearchListActivity::class.java)
//            intent.putExtra("search", str)
//            startActivity(intent)
//        }
    }
}