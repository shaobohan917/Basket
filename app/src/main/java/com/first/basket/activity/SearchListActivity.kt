package com.first.basket.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.first.basket.R
import com.first.basket.adapter.ContentAdapter
import com.first.basket.base.BaseActivity
import com.first.basket.bean.ClassifyContentBean
import com.first.basket.bean.ProductBean
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.LogUtils
import kotlinx.android.synthetic.main.activity_search_list.*
import java.util.*

/**
 * Created by hanshaobo on 22/10/2017.
 */
class SearchListActivity:BaseActivity() {

    private var mContentDatas = ArrayList<ProductBean>()

    private lateinit var mContentAdapter: ContentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_list)
        initView()
        initData()
    }

    private fun initView() {
        contentRecyclerView.layoutManager = LinearLayoutManager(this@SearchListActivity)
        mContentAdapter = ContentAdapter(this@SearchListActivity, mContentDatas)
        contentRecyclerView.adapter = mContentAdapter

    }

    private fun initData() {
        var search = intent.getStringExtra("search")
        if(TextUtils.isEmpty(search)){
            search = "瓜"
        }
        LogUtils.d("search:"+search)
        getProduct(search)
    }


    /**
     * 搜索商品列表
     */
    private fun getProduct(searchString: String) {
        HttpMethods.createService().getProducts("get_products", "", "", searchString, "")
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<ClassifyContentBean>() {
                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable) {
                    }

                    override fun onNext(t: ClassifyContentBean) {
                        LogUtils.d("获取到几条：" + t.result.data.size)
                        mContentDatas.clear()
                        val dataBean = t.result.data
                        for (i in 0 until dataBean.size) {
                            mContentDatas.add(dataBean[i])
                        }
                        mContentAdapter.notifyDataSetChanged()
                    }
                })
    }
}