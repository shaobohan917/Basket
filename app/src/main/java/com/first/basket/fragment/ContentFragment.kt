package com.first.basket.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.first.basket.R
import com.first.basket.activity.GoodsDetailActivity
import com.first.basket.activity.MainActivity
import com.first.basket.adapter.ContentAdapter
import com.first.basket.adapter.SecondAdapter
import com.first.basket.app.BaseApplication
import com.first.basket.base.HttpResult
import com.first.basket.bean.ClassifyBean
import com.first.basket.bean.ClassifyContentBean
import com.first.basket.bean.HotRecommendBean
import com.first.basket.bean.ProductBean
import com.first.basket.common.CommonMethod
import com.first.basket.common.CommonMethod1
import com.first.basket.common.StaticValue
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.ImageUtils
import com.first.basket.utils.LogUtils
import com.first.basket.utils.SPUtil
import com.first.basket.utils.ToastUtil
import com.github.ybq.android.spinkit.style.DoubleBounce
import kotlinx.android.synthetic.main.fragment_content.*
import kotlinx.android.synthetic.main.layout_loading.*
import java.util.*

@SuppressLint("ValidFragment")
/**
 * Created by hanshaobo on 17/09/2017.
 */
class ContentFragment(activity: MainActivity, data: ClassifyBean.DataBean) : BaseFragment() {
    private var activity = activity
    private var mDatas = data

    private var hotData: HotRecommendBean.ResultBean.DataBean? = null

    private lateinit var mContentAdapter: ContentAdapter
    private lateinit var mSecondAdapter: SecondAdapter

    private var mContentDatas = ArrayList<ProductBean>()
    private var mSecondDatas = ArrayList<ClassifyBean.DataBean.LeveltwoBean>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        contentRecyclerView.layoutManager = LinearLayoutManager(activity)
        secondRecyclerView.layoutManager = GridLayoutManager(activity, 3)
    }

    private fun initData() {
        //初始化商品列表adapter
        mContentAdapter = ContentAdapter(activity, mContentDatas)
        contentRecyclerView.adapter = mContentAdapter

        mSecondAdapter = SecondAdapter(activity, mSecondDatas)
        mSecondAdapter.setOnItemClickListener { view, data, position ->
            var id = data.leveltwoid
            var twoidSb = StringBuilder()
            if ("000".equals(id)) {
                for (i in 0 until mDatas.leveltwo.size) {
                    twoidSb.append(mDatas.leveltwo[i].leveltwoid).append(",")
                }
                var str = twoidSb.toString()
                str = str.substring(0, twoidSb.length - 1)
                getProduct(str)
            } else {
                getProduct(id)
            }

        }
        secondRecyclerView.adapter = mSecondAdapter

        mContentAdapter.setOnItemClickListener { view, data, position ->
            var intent = Intent(activity, GoodsDetailActivity::class.java)
            intent.putExtra("id", data.productid)
            startActivity(intent)
        }

        mContentAdapter.setOnAddItemClickListener { view, data, position ->
            if (CommonMethod.isTrue(data.promboolean)) {
                if ("荤".equals(data.promdata.promproducttype) && !SPUtil.getBoolean(StaticValue.PROM_HUN, false)) {
                    CommonMethod1.addGoodToCar(view.findViewById(R.id.ivGoods), rlRoot, ivCar, null)
                    addData(data)
                    SPUtil.setBoolean(StaticValue.PROM_HUN, true)
                } else if ("素".equals(data.promdata.promproducttype) && !SPUtil.getBoolean(StaticValue.PROM_SU, false)) {
                    CommonMethod1.addGoodToCar(view.findViewById(R.id.ivGoods), rlRoot, ivCar, null)
                    addData(data)
                    SPUtil.setBoolean(StaticValue.PROM_SU, true)
                } else {
                    ToastUtil.showToast("特惠商品荤素各只可添加一件")
                }
            } else {
                CommonMethod1.addGoodToCar(view.findViewById(R.id.ivGoods), rlRoot, ivCar, null)
                addData(data)
            }
        }
    }

    private fun addData(data: ProductBean) {
        BaseApplication.getInstance().addProduct(data)
        activity.setCountAdd()
    }


    /**
     * 获取商品列表
     */
    private fun getProduct(leveltwoId: String) {
//        loadingView.visibility = View.VISIBLE
//        loadingView.setIndeterminateDrawable(DoubleBounce())
        HttpMethods.createService().getProducts("get_products", activity.mChannel.toString(), leveltwoId, "", "")
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<ClassifyContentBean>>() {
                    override fun onNext(t: HttpResult<ClassifyContentBean>) {
                        super.onNext(t)
                        mContentDatas.clear()
                        val dataBean = t.result.data

                        for (i in 0 until dataBean.size) {
                            mContentDatas.add(dataBean[i])
                        }
                        mContentAdapter.notifyDataSetChanged()
                    }

                    override fun onCompleted() {
                        super.onCompleted()
//                        loadingView.visibility = View.GONE
                    }
                })
    }

    fun setContentData(position: Int, dataBean: ClassifyBean.DataBean) {
        if (position != 0) {
            var twoidSb = StringBuilder()
            for (i in 0 until mDatas.leveltwo.size) {
                twoidSb.append(mDatas.leveltwo[i].leveltwoid).append(",")
            }
            var str = twoidSb.toString()
            str = str.substring(0, twoidSb.length - 1)
            getProduct(str)

            var leveltwobean = ClassifyBean.DataBean.LeveltwoBean()
            leveltwobean.leveltwodesc = "全部"
            leveltwobean.leveltwoid = "000"
            mSecondDatas.add(leveltwobean)
            mSecondDatas.addAll(dataBean.leveltwo)
            mSecondAdapter.notifyDataSetChanged()

            showHotImg(false)
        } else {
            showHotImg(true)
        }
    }

    fun showHotImg(b: Boolean) {
        if (b) {
            if (hotData != null) {
                ImageUtils.showImg(activity, hotData?.hotimage, ivHot)
                ivHot.visibility = View.VISIBLE
                secondRecyclerView.visibility = View.GONE
            }
        } else {
            ivHot.visibility = View.GONE
            secondRecyclerView.visibility = View.VISIBLE
        }
    }


    fun getHotRecommend(activity: MainActivity) {
//        loadingView.visibility = View.VISIBLE
//        loadingView.setIndeterminateDrawable(DoubleBounce())
        HttpMethods.createService()
                .getHotRecommend("get_hotrecommend", activity.mChannel.toString())
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HotRecommendBean>() {
                    override fun onNext(t: HotRecommendBean) {
                        super.onNext(t)
                        setRecommendData(t.result.data)
                    }

                    override fun onCompleted() {
                        super.onCompleted()
                    }
                })
    }

    private fun setRecommendData(data: HotRecommendBean.ResultBean.DataBean) {
        this.hotData = data
        showHotImg(true)
        ImageUtils.showImg(activity, data.hotimage, ivHot)
        mContentDatas.clear()
        mContentAdapter = ContentAdapter(activity, mContentDatas)
        mContentDatas.addAll(data.products)
        mContentAdapter.notifyDataSetChanged()
    }
}
