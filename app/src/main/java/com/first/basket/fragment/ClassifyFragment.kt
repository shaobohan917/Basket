package com.first.basket.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.first.basket.R
import com.first.basket.base.BaseRecyclerAdapter
import com.first.basket.bean.BaseBean
import com.first.basket.bean.CategoryContentBean
import com.first.basket.utils.LogUtils
import kotlinx.android.synthetic.main.fragment_classify.*
import kotlinx.android.synthetic.main.item_recycler_category.view.*
import kotlinx.android.synthetic.main.item_recycler_content.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hanshaobo on 30/08/2017.
 */
class ClassifyFragment : BaseFragment() {
    private var mCategoryDatas = ArrayList<BaseBean>()
    private var mContentDatas = ArrayList<CategoryContentBean>()
    private var mCategorys = arrayOf("热门推荐", "蔬菜专区", "有机专区", "生鲜专区", "粮油米面", "牛奶饮品", "南北干货", "厨房调味", "豆制品", "热门推荐", "蔬菜专区", "有机专区", "生鲜专区", "粮油米面", "牛奶饮品", "南北干货", "厨房调味", "豆制品")
    private var mContents = arrayOf("奇异果", "香蕉", "苹果")

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

        for (i in mCategorys) {
            var bean = BaseBean()
            bean.category = i
            mCategoryDatas.add(bean)
        }
        val categoryAdapter = BaseRecyclerAdapter(R.layout.item_recycler_category, mCategoryDatas) { view: View, item: BaseBean ->
            view.tvCategory.text = item.category
            view.onClick {
                var count = categoryRecyclerView.childCount
                LogUtils.d("count:" + count)
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
                var contentBean = CategoryContentBean()
                contentBean.name = "hehe"
                contentBean.num = 1
                contentBean.price = 2f

                mContentDatas.add(contentBean)
            }
        }
        categoryRecyclerView.adapter = categoryAdapter


        //内容
        for (j in mContents) {
            var bean = CategoryContentBean()
            bean.name = j
            bean.num = 1
            bean.price = 1f
            mContentDatas.add(bean)
        }
        var contentAdapter = BaseRecyclerAdapter(R.layout.item_recycler_content, mContentDatas) { view, categoryContentBean ->
            view.tvName.text = categoryContentBean.name
            view.tvUnit.text = categoryContentBean.num.toString()
            view.tvPrice.text = categoryContentBean.price.toString()
        }
        contentRecyclerView.adapter = contentAdapter
    }
}