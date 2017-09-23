package com.first.basket.fragment

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.first.basket.R
import com.first.basket.base.BaseRecyclerAdapter
import com.first.basket.base.HttpResult
import com.first.basket.bean.ClassifyBean
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import kotlinx.android.synthetic.main.fragment_classify.*
import kotlinx.android.synthetic.main.item_recycler_category.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
* Created by hanshaobo on 30/08/2017.
*/
class ClassifyFragment : BaseFragment() {
    private lateinit var mCategoryAdapter: BaseRecyclerAdapter<ClassifyBean.DataBean, BaseRecyclerAdapter.ViewHolder<ClassifyBean.DataBean>>
    private var mCategoryDatas = ArrayList<ClassifyBean.DataBean>()
    //存储商品分类id
    private var mIds = ArrayList<String>()
    //当前选中的分类
    private var mClassifyPosition = 0
    var fragmentList = ArrayList<ContentFragment>()

    private var indexList = ArrayList<Int>()

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
    }


    private fun initData() {
        //初始化分类列表adapter
        mCategoryAdapter = BaseRecyclerAdapter(R.layout.item_recycler_category, mCategoryDatas) { view: View, item: ClassifyBean.DataBean ->
            view.tvCategory.text = item.levelonedesc
            view.onClick {
                var index = categoryRecyclerView.getChildAdapterPosition(view)
                if (mClassifyPosition != index) {
                    setSelected(index)
                    //更新内容页
                    refreshContent(index)
                }
            }
        }
        categoryRecyclerView.adapter = mCategoryAdapter

        //获取分类列表
        getClassify()
    }

    private fun getClassify() {
        //获取商品分类
        HttpMethods.createService().getClassify("get_productclassification")
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<ClassifyBean>>() {
                    override fun onNext(t: HttpResult<ClassifyBean>) {
                        super.onNext(t)
                        for (i in 0 until t.result.data.size) {
                            mCategoryDatas.add(t.result.data[i])
                            mIds.add(t.result.data[i].leveloneid)
                        }
                        mCategoryAdapter.notifyDataSetChanged()

                        //有多少分类，创建多少fragment
                        for (i in 0 until t.result.data.size) {
                            var fragment = ContentFragment()
                            fragmentList.add(fragment)
                        }

                        //选中第一个分类
                        Handler().postDelayed({
                            setSelected(0)
                            categoryRecyclerView.getChildAt(0).performClick()
                        }, 500)
                    }
                })
    }


    /**
     * 设置选中，其他全部未选中
     */
    private fun setSelected(index: Int) {
        var count = categoryRecyclerView.childCount
        for (i in 0 until count) {
            val view = categoryRecyclerView.getChildAt(i)
            val tvCategory = view.findViewById<TextView>(R.id.tvCategory)
            tvCategory.setBackgroundColor(activity.resources.getColor(R.color.text_bg))
            tvCategory.setCompoundDrawables(null, null, null, null)
        }
        val drawable = activity.resources.getDrawable(R.drawable.category_line)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val view = categoryRecyclerView.getChildAt(index)
        view.tvCategory.setCompoundDrawables(drawable, null, null, null)
        view.tvCategory.setBackgroundColor(activity.resources.getColor(R.color.white))
    }


    private fun refreshContent(l: Int) {
        val fragment = fragmentList[l]

        replaceContent(fragment, R.id.fragmentContainer1)
        //设置数据
        if (!indexList.contains(l)) {
            Handler().postDelayed({
                fragmentList[l].setContentData(mCategoryDatas[l])
            }, 500)
            indexList.add(l)
        }
    }
}
