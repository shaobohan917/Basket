package com.first.basket.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.first.basket.R
import com.first.basket.adapter.CategoryRecyclerViewAdapter
import com.first.basket.adapter.ContentRecyclerViewAdapter
import com.first.basket.bean.BaseBean
import com.first.basket.bean.CategoryContentBean
import com.first.basket.listener.OnItemClicklistener
import com.first.basket.utils.LogUtils

/**
 * Created by hanshaobo on 30/08/2017.
 */
class ClassifyFragment : BaseFragment() {
    private lateinit var llManager: LinearLayoutManager
    private lateinit var mCategoryRecyclerView: RecyclerView
    private lateinit var mContentRecyclerView: RecyclerView
    private lateinit var mCategoryAdapter: CategoryRecyclerViewAdapter
    private lateinit var mContentAdapter: ContentRecyclerViewAdapter
    private var mDatas = ArrayList<BaseBean>()
    private var mContentDatas = ArrayList<CategoryContentBean>()
    private var categorys = arrayOf("热门推荐", "蔬菜专区", "有机专区", "生鲜专区", "粮油米面", "牛奶饮品", "南北干货", "厨房调味", "豆制品", "热门推荐", "蔬菜专区", "有机专区", "生鲜专区", "粮油米面", "牛奶饮品", "南北干货", "厨房调味", "豆制品")
    private var contents = arrayOf("奇异果", "香蕉", "苹果")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.fragment_classify, container, false)!!
        mCategoryRecyclerView = view.findViewById(R.id.categoryRecyclerView)
        mContentRecyclerView = view.findViewById(R.id.contentRecyclerView)

        var drawable = activity.resources.getDrawable(R.mipmap.ic_category_search)
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        view.findViewById<EditText>(R.id.etSearch).setCompoundDrawables(drawable, null, null, null)
        initView()
        initData()
        return view
    }

    private fun initView() {
        llManager = LinearLayoutManager(activity)
        mCategoryRecyclerView.layoutManager = llManager
        mContentRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun initData() {
        class listener : OnItemClicklistener {
            override fun onItemClick(itemView: View, position: Int) {
                Log.d("luka", "点击了:" + position + "count:" + mCategoryRecyclerView.childCount)
                for (i in 0..5) {
                    val view = mCategoryRecyclerView.getChildAt(i)
                    LogUtils.d("view:" + view)
                    val tvCategory = view.findViewById<TextView>(R.id.tvCategory)
                    tvCategory.setBackgroundColor(activity.resources.getColor(R.color.text_bg))
                    view.findViewById<TextView>(R.id.tvCategory).setCompoundDrawables(null, null, null, null)
                }
                val tvCategory = itemView.findViewById<TextView>(R.id.tvCategory)
                val drawable = activity.resources.getDrawable(R.drawable.category_line)
                drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                tvCategory.setCompoundDrawables(drawable, null, null, null)
                tvCategory.setBackgroundColor(activity.resources.getColor(R.color.white))

                //更新内容页
                var contentBean = CategoryContentBean()
                contentBean.name = "hehe"
                contentBean.num = 1
                contentBean.price = 2f

                mContentDatas.add(contentBean)

                mContentAdapter.notifyDataSetChanged()

            }
        }

        for (i in categorys) {
            var bean = BaseBean()
            bean.category = i
            mDatas.add(bean)
        }

        mCategoryAdapter = CategoryRecyclerViewAdapter(mDatas, listener = listener())
        mCategoryRecyclerView.adapter = mCategoryAdapter


        //内容
        for (j in contents) {
            var bean = CategoryContentBean()
            bean.name = j
            bean.num = 1
            bean.price = 1f
            mContentDatas.add(bean)
        }
        mContentAdapter = ContentRecyclerViewAdapter(mContentDatas)
        mContentRecyclerView.adapter = mContentAdapter


    }


}