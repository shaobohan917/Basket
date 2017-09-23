package com.first.basket.fragment

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.first.basket.R
import com.first.basket.base.BaseRecyclerAdapter
import com.first.basket.bean.ClassifyBean
import com.first.basket.bean.ClassifyContentBean
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.LogUtils
import com.first.basket.utils.ScreenUtils
import kotlinx.android.synthetic.main.item_recycler_content.view.*
import kotlinx.android.synthetic.main.item_recycler_second.view.*
import org.jetbrains.anko.padding
import org.jetbrains.anko.wrapContent
import java.util.*

/**
 * Created by hanshaobo on 17/09/2017.
 */
class ContentFragment : BaseFragment() {

    private lateinit var mContentAdapter: BaseRecyclerAdapter<ClassifyContentBean.ResultBean.DataBean, BaseRecyclerAdapter.ViewHolder<ClassifyContentBean.ResultBean.DataBean>>
    private lateinit var mSecondAdapter: BaseRecyclerAdapter<ClassifyBean.DataBean.LeveltwoBean, BaseRecyclerAdapter.ViewHolder<ClassifyBean.DataBean.LeveltwoBean>>
    private var mContentDatas = ArrayList<ClassifyContentBean.ResultBean.DataBean>()
    private var mSecondDatas = ArrayList<ClassifyBean.DataBean.LeveltwoBean>()

    private lateinit var contentRecyclerView1: RecyclerView
    private lateinit var secondRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentRecyclerView1 = view.findViewById(R.id.contentRecyclerView1)
        secondRecyclerView = view.findViewById(R.id.secondRecyclerView)

        initView()
        initData()
    }

    private fun initView() {
        contentRecyclerView1.layoutManager = LinearLayoutManager(activity)
        secondRecyclerView.layoutManager = GridLayoutManager(activity, 3)
    }

    private fun initData() {
        //初始化商品列表adapter
        mContentAdapter = BaseRecyclerAdapter(R.layout.item_recycler_content, mContentDatas) { view: View, bean: ClassifyContentBean.ResultBean.DataBean ->
            view.tvName.text = bean.productname
            view.tvUnit.text = bean.unit
            view.tvPrice.text = bean.price

            view.amoutView.setGoods_storage(10)
        }
        contentRecyclerView1.adapter = mContentAdapter

        mSecondAdapter = BaseRecyclerAdapter(R.layout.item_recycler_second, mSecondDatas) { view, leveltwoBean ->
            view.tvSecondLevel.text = leveltwoBean.leveltwodesc
        }
        secondRecyclerView.adapter = mSecondAdapter
    }


    /**
     * 获取商品列表
     */
    private fun getProduct(id: String) {
        HttpMethods.createService().getProducts("get_products", id)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<ClassifyContentBean>() {
                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable) {
                    }

                    override fun onNext(t: ClassifyContentBean) {
                        val dataBean = t.result.data

                        for (i in 0 until 10) {
//                        for (i in 0 until dataBean.size) {
                            mContentDatas.add(dataBean[i])
                        }
                        mContentAdapter.notifyDataSetChanged()
                    }
                })
    }

    fun setContentData(dataBean: ClassifyBean.DataBean) {
        getProduct(dataBean.leveloneid)

        mSecondDatas.addAll(dataBean.leveltwo)
        mSecondAdapter.notifyDataSetChanged()

    }
}