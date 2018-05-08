package com.first.basket.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import com.first.basket.R
import com.first.basket.adapter.ContentAdapter
import com.first.basket.app.SampleApplicationLike
import com.first.basket.base.BaseActivity
import com.first.basket.base.HttpResult
import com.first.basket.bean.ClassifyContentBean
import com.first.basket.bean.ProductBean
import com.first.basket.common.CommonMethod
import com.first.basket.common.CommonMethod1
import com.first.basket.common.StaticValue
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.SPUtil
import com.first.basket.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_search_list.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import q.rorbin.badgeview.QBadgeView
import java.util.*

/**
 * Created by hanshaobo on 22/10/2017.
 */
class SearchListActivity : BaseActivity() {

    private var mContentDatas = ArrayList<ProductBean>()
    private var mCount = 0

    private lateinit var badgeView: QBadgeView

    private lateinit var mContentAdapter: ContentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_list)
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        contentRecyclerView.layoutManager = LinearLayoutManager(this@SearchListActivity)
        mContentAdapter = ContentAdapter(this@SearchListActivity, mContentDatas)
        contentRecyclerView.adapter = mContentAdapter

        badgeView = QBadgeView(this@SearchListActivity)
    }

    private fun initData() {
        var search = intent.getStringExtra("search")
        getProduct(search)
        mCount = SampleApplicationLike.getInstance().productsCount
        badgeView.bindTarget(tvCount).badgeNumber = mCount
    }


    private fun initListener() {
        mContentAdapter.setOnItemClickListener { view, data, position ->
            var intent = Intent(this@SearchListActivity, GoodsDetailActivity::class.java)
            intent.putExtra("id", data.productid)
            startActivity(intent)
        }
        mContentAdapter.setOnAddItemClickListener { view, data, position ->
            var ivGoods = view.findViewById<ImageView>(R.id.ivGoods)

            if (CommonMethod.isTrue(data.promboolean)) {
                if ("荤" == data.promdata.promproducttype && !SPUtil.getBoolean(StaticValue.PROM_HUN, false)) {
                    CommonMethod1.addGoodToCar(ivGoods, rlRoot, ivCar, object : CommonMethod1.Companion.OnAddListener {
                        override fun onAdd() {
                            mCount++
                            tvCount.text = mCount.toString()
                            badgeView.bindTarget(tvCount).badgeNumber = mCount

                            SampleApplicationLike.getInstance().addProduct(data)
                            MainActivity.getInstance1().setCountAdd()
                            SPUtil.setBoolean(StaticValue.PROM_HUN, true)
                            SPUtil.setString(StaticValue.GET_TIME, CommonMethod.getTime(false))
                        }
                    })
                } else if ("素" == data.promdata.promproducttype && !SPUtil.getBoolean(StaticValue.PROM_SU, false)) {
                    CommonMethod1.addGoodToCar(ivGoods, rlRoot, ivCar, object : CommonMethod1.Companion.OnAddListener {
                        override fun onAdd() {
                            mCount++
                            tvCount.text = mCount.toString()
                            badgeView.bindTarget(tvCount).badgeNumber = mCount

                            SampleApplicationLike.getInstance().addProduct(data)
                            MainActivity.getInstance1().setCountAdd()
                            SPUtil.setBoolean(StaticValue.PROM_SU, true)
                            SPUtil.setString(StaticValue.GET_TIME, CommonMethod.getTime(false))
                        }
                    })
                } else {
                    ToastUtil.showToast(getString(R.string.one_oneday))
                }
            } else {
                CommonMethod1.addGoodToCar(ivGoods, rlRoot, ivCar, object : CommonMethod1.Companion.OnAddListener {
                    override fun onAdd() {
                        mCount++
                        tvCount.text = mCount.toString()
                        badgeView.bindTarget(tvCount).badgeNumber = mCount

                        SampleApplicationLike.getInstance().addProduct(data)
                        MainActivity.getInstance1().setCountAdd()
                    }
                })
            }
        }

        ivCar.onClick {
            setResult(Activity.RESULT_OK)
            MainActivity.getInstance1().setCurrentPage(3)
            myFinish()
        }

        ivBack.onClick {
            myFinish()
        }

        etSearch.setOnEditorActionListener { p0, p1, p2 ->
            if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                CommonMethod.hideKeyboard(etSearch)
                getProduct(p0.text.toString())
            }
            false
        }

    }


    /**
     * 搜索商品列表
     */
    private fun getProduct(searchString: String) {
        HttpMethods.createService().getProducts("get_products", "", "", searchString, "")
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<ClassifyContentBean>>() {
                    override fun onNext(t: HttpResult<ClassifyContentBean>) {
                        super.onNext(t)
                        if (t.status == 0) {
                            mContentDatas.clear()
                            val dataBean = t.result.data
                            for (i in 0 until dataBean.size) {
                                mContentDatas.add(dataBean[i])
                            }
                            mContentAdapter.notifyDataSetChanged()
                        } else {
                            ToastUtil.showToast("暂无搜索结果")
                        }
                    }
                })
    }
}