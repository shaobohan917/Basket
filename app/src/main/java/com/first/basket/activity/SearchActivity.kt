package com.first.basket.activity

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.first.basket.R
import com.first.basket.base.BaseActivity
import com.first.basket.bean.RecommendBean
import com.first.basket.common.StaticValue
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.SPUtil
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.item_et_search.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

/**
 * Created by hanshaobo on 12/09/2017.
 */
class SearchActivity : BaseActivity() {
    private lateinit var mInflater: LayoutInflater

    private lateinit var mHistoryAdapter: TagAdapter<String>
    private lateinit var mRecommendAdapter: TagAdapter<String>
    private var mHistoryDatas = ArrayList<String>()
    private var mRecommendDatas = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        mInflater = LayoutInflater.from(this@SearchActivity)
    }

    private fun initData() {
        mHistoryAdapter = object : TagAdapter<String>(mHistoryDatas) {
            override fun getView(parent: FlowLayout, position: Int, str: String): View {
                val tvTag = mInflater.inflate(R.layout.item_text, null) as TextView
                tvTag.text = str
                return tvTag
            }
        }
        mRecommendAdapter = object : TagAdapter<String>(mRecommendDatas) {
            override fun getView(parent: FlowLayout, position: Int, str: String): View {
                val tvTag = mInflater.inflate(R.layout.item_text, null) as TextView
                tvTag.text = str
                return tvTag
            }
        }

        flowLayout.adapter = mHistoryAdapter
        flowLayout1.adapter = mRecommendAdapter

        getRecommend()
    }

    private fun initListener() {
        flowLayout.setOnTagClickListener { view, position, parent ->
            goSearchList(mHistoryDatas[position])
            true
        }
        flowLayout1.setOnTagClickListener { view, position, parent ->
            goSearchList(mRecommendDatas[position])
            true
        }

        etSearch.setOnEditorActionListener { p0, p1, p2 ->
            if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                goSearchList(p0.text.toString())
                mHistoryDatas.add(p0.text.toString())
                SPUtil.setString(StaticValue.SEARCH, mHistoryDatas.toString())
            }
            false
        }

        ivEmpty.onClick {
            if (mHistoryDatas.size == 0) return@onClick
            showDialog("确定清空历史搜索？", DialogInterface.OnClickListener { p0, p1 ->
                mHistoryDatas.clear()
                mHistoryAdapter.notifyDataChanged()
            })
        }
    }

    private fun goSearchList(str: String) {
        var intent = Intent(this@SearchActivity, SearchListActivity::class.java)
        intent.putExtra("search", str)
        myStartActivityForResult(intent, REQUEST_SPE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode) {
            myFinish()
        }
    }


    private fun getRecommend() {
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
        for (datum in data) {
            mRecommendDatas.add(datum.productname)
        }
        mRecommendAdapter.notifyDataChanged()
    }

    override fun onResume() {
        super.onResume()
        //获取
        var count = SPUtil.getInt(StaticValue.SEARCH_COUNT, mHistoryDatas.size)
        mHistoryDatas.clear()
        for (i in 0 until count) {
            var str = SPUtil.getString(StaticValue.SEARCH + i, "")
            mHistoryDatas.add(str)
        }
        Collections.reverse(mHistoryDatas)
        mHistoryAdapter.notifyDataChanged()
    }

    override fun onStop() {
        super.onStop()
        //保存
        SPUtil.setInt(StaticValue.SEARCH_COUNT, mHistoryDatas.size)
        for (i in 0 until mHistoryDatas.size) {
            SPUtil.setString(StaticValue.SEARCH + i, mHistoryDatas[i])
        }
    }
}