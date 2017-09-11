package com.first.basket.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.first.basket.R
import com.first.basket.base.BaseRecyclerAdapter
import com.first.basket.bean.ClassifyBean
import com.first.basket.bean.ClassifyContentBean
import com.first.basket.http.HttpMethods
import com.first.basket.http.TransformUtils
import com.first.basket.utils.LogUtils
import kotlinx.android.synthetic.main.fragment_classify.*
import kotlinx.android.synthetic.main.item_recycler_category.view.*
import kotlinx.android.synthetic.main.item_recycler_content.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import rx.Subscriber
import java.util.*

/**
 * Created by hanshaobo on 30/08/2017.
 */
class ClassifyFragment : BaseFragment() {
    private lateinit var categoryAdapter: BaseRecyclerAdapter<ClassifyBean.ResultBean.DataBean, BaseRecyclerAdapter.ViewHolder<ClassifyBean.ResultBean.DataBean>>

    private var mCategoryDatas = ArrayList<ClassifyBean.ResultBean.DataBean>()
    private var mContentDatas = ArrayList<ClassifyContentBean.ResultBean.DataBean>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_classify, container, false)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }


    private fun initView() {
        val drawable = activity.resources.getDrawable(R.mipmap.ic_category_search)
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        etSearch.setCompoundDrawables(drawable, null, null, null)

        categoryRecyclerView.layoutManager = LinearLayoutManager(activity)
        contentRecyclerView.layoutManager = LinearLayoutManager(activity)
    }


    private fun initData() {
        categoryAdapter = BaseRecyclerAdapter(R.layout.item_recycler_category, mCategoryDatas) { view: View, item: ClassifyBean.ResultBean.DataBean ->
            view.tvCategory.text = item.classification
            view.onClick {
                var count = categoryRecyclerView.childCount
                for (i in 0..count - 1) {
                    val view = categoryRecyclerView.getChildAt(i)
                    val tvCategory = view.findViewById<TextView>(R.id.tvCategory)
                    tvCategory.setBackgroundColor(activity.resources.getColor(R.color.text_bg))
                    tvCategory.setCompoundDrawables(null, null, null, null)
                }
                val drawable = activity.resources.getDrawable(R.drawable.category_line)
                drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                view.tvCategory.setCompoundDrawables(drawable, null, null, null)
                view.tvCategory.setBackgroundColor(activity.resources.getColor(R.color.white))

                //更新内容页
                getProduct(item.id!!)
            }
        }
        categoryRecyclerView.adapter = categoryAdapter

        HttpMethods.createService().getClassify("get_productclassification")
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(Sub())
    }

    inner class Sub : Subscriber<ClassifyBean>() {
        override fun onNext(t: ClassifyBean) {
            t.result?.data?.let { mCategoryDatas.addAll(it) }
            categoryAdapter.notifyDataSetChanged()
        }

        override fun onError(e: Throwable) {
            LogUtils.d("onError")
        }

        override fun onCompleted() {

        }

    }

    inner class ContentSubscriber : Subscriber<ClassifyContentBean>() {
        override fun onCompleted() {
        }

        override fun onError(e: Throwable) {
            LogUtils.d("onError" + e.message)
        }

        override fun onNext(t: ClassifyContentBean) {
            val dataBean = t.result.data
            mContentDatas.addAll(dataBean)
            for (i in 0 until mContentDatas.size) {
                LogUtils.d("data:" + mContentDatas[i].productname)
            }
            //内容
            val contentAdapter = BaseRecyclerAdapter(R.layout.item_recycler_content, mContentDatas) { view: View, bean: ClassifyContentBean.ResultBean.DataBean ->
                view.tvName.text = bean.productname
                view.tvUnit.text = bean.unit
                view.tvPrice.text = bean.price
            }
            contentRecyclerView.adapter = contentAdapter
        }
    }

    fun getProduct(id: String) {
        HttpMethods.createService().getProducts("get_products", "68")
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(ContentSubscriber())

    }
}
