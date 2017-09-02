package com.first.basket.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.first.basket.R
import com.first.basket.adapter.CategoryRecyclerViewAdapter
import com.first.basket.adapter.ContentRecyclerViewAdapter
import com.first.basket.bean.BaseBean
import com.first.basket.bean.CategoryContentBean
import com.first.basket.listener.OnItemClicklistener

/**
 * Created by hanshaobo on 30/08/2017.
 */
class ClassifyFragment : BaseFragment() {
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var contentRecyclerView: RecyclerView
    private var mDatas = ArrayList<BaseBean>()
    private var mContentDatas = ArrayList<CategoryContentBean>()
    private var categorys = arrayOf("热门推荐", "蔬菜专区", "有机专区", "生鲜专区", "粮油米面", "牛奶饮品", "南北干货", "厨房调味", "豆制品", "热门推荐", "蔬菜专区", "有机专区", "生鲜专区", "粮油米面", "牛奶饮品", "南北干货", "厨房调味", "豆制品")
    private var contents = arrayOf("奇异果", "香蕉", "苹果")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(R.layout.fragment_classify, container, false)!!
        categoryRecyclerView = view.findViewById<RecyclerView>(R.id.categoryRecyclerView)
        contentRecyclerView = view.findViewById<RecyclerView>(R.id.contentRecyclerView)

        var drawable = activity.resources.getDrawable(R.mipmap.ic_category_search)
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        view.findViewById<EditText>(R.id.etSearch).setCompoundDrawables(drawable, null, null, null)
        initView()
        initData()
        return view
    }

    private fun initView() {
        categoryRecyclerView.layoutManager = LinearLayoutManager(activity)
        contentRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun initData() {
        for (i in categorys) {
            var bean = BaseBean()
            bean.category = i
            mDatas.add(bean)
        }
        class listener : OnItemClicklistener {
            override fun onItemClick(itemView: View, position: Int) {
                itemView.setBackgroundColor(activity.resources.getColor(R.color.black))
                Log.d("luka", "点击了:" + position)
            }
        }
        categoryRecyclerView.adapter = CategoryRecyclerViewAdapter(mDatas, listener = listener())

        //内容
        for (j in contents) {
            var bean = CategoryContentBean()
            bean.name = j
            bean.num = 1
            bean.price = 1f
            mContentDatas.add(bean)
        }
        contentRecyclerView.adapter = ContentRecyclerViewAdapter(mContentDatas)
    }
}