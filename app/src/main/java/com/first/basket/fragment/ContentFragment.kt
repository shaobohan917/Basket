package com.first.basket.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.ScaleAnimation
import com.first.basket.R
import com.first.basket.adapter.ContentAdapter
import com.first.basket.base.BaseRecyclerAdapter
import com.first.basket.bean.ClassifyBean
import com.first.basket.bean.ClassifyContentBean
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.LogUtils
import kotlinx.android.synthetic.main.item_recycler_second.view.*
import java.util.*

/**
 * Created by hanshaobo on 17/09/2017.
 */
class ContentFragment : BaseFragment() {
    private lateinit var mContentAdapter: ContentAdapter
    private lateinit var mSecondAdapter: BaseRecyclerAdapter<ClassifyBean.DataBean.LeveltwoBean, BaseRecyclerAdapter.ViewHolder<ClassifyBean.DataBean.LeveltwoBean>>
    private var mContentDatas = ArrayList<ClassifyContentBean.ResultBean.DataBean>()
    private var mSecondDatas = ArrayList<ClassifyBean.DataBean.LeveltwoBean>()

    private lateinit var contentRecyclerView: RecyclerView
    private lateinit var secondRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentRecyclerView = view.findViewById(R.id.contentRecyclerView)
        secondRecyclerView = view.findViewById(R.id.secondRecyclerView)

        initView()
        initData()
        initListener()
    }

    private fun initListener() {
        contentRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    LogUtils.d("收起二级分类:" + dy)
                } else if (dy < 0) {
                    LogUtils.d("展示二级分类:" + dy)
                }
            }
        })

    }

    private fun initView() {
        contentRecyclerView.layoutManager = LinearLayoutManager(activity)
        secondRecyclerView.layoutManager = GridLayoutManager(activity, 3)
    }

    private fun initData() {
        //初始化商品列表adapter
        mContentAdapter = ContentAdapter(activity, mContentDatas)
        contentRecyclerView.adapter = mContentAdapter

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

                        for (i in 0 until dataBean.size) {
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

        getSecondHeight()

    }

    private fun getSecondHeight() {

        var anim = ScaleAnimation(0f,0f,500f,500f)
        secondRecyclerView.animation = anim
        anim.start()


//        LogUtils.d("height:"+height)
//        LogUtils.d("h:"+h)
    }
}