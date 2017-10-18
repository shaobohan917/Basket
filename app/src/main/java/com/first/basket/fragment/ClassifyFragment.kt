package com.first.basket.fragment

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.first.basket.R
import com.first.basket.activity.MainActivity
import com.first.basket.adapter.ClassifyAdapter
import com.first.basket.base.HttpResult
import com.first.basket.bean.ClassifyBean
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.LogUtils
import kotlinx.android.synthetic.main.fragment_classify.*
import kotlinx.android.synthetic.main.item_recycler_category.view.*

/**
 * Created by hanshaobo on 30/08/2017.
 */
class ClassifyFragment : BaseFragment() {
    private var mClassifyDatas = ArrayList<ClassifyBean.DataBean>()
    //存储商品分类id
    private var mIds = ArrayList<String>()

    private var fragmentList = ArrayList<BaseFragment>()

    private var indexList = ArrayList<Int>()

    private lateinit var mClassifyAdapter: ClassifyAdapter

    private var isGetedRecommend: Boolean = false

    //1：社区菜市；2：上海菜市 ；3：全国菜市
    private var preType = 1

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_classify, container, false)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preType = (activity as MainActivity).mChannel
        initView()
        initData()
    }

    private fun initView() {
        val drawable = activity.resources.getDrawable(R.mipmap.ic_category_search)
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        etSearch.setCompoundDrawables(drawable, null, null, null)

        classifyRecyclerView.layoutManager = LinearLayoutManager(activity)
    }


    private fun initData() {
        //初始化分类列表adapter
        mClassifyAdapter = ClassifyAdapter(activity, mClassifyDatas)
        classifyRecyclerView.adapter = mClassifyAdapter
        mClassifyAdapter.setOnItemClickListener { view, data, position ->
            refreshContent(position)
        }

        //获取分类列表
        getClassify(preType, true)
    }

    private fun getClassify(channel: Int, needRefresh: Boolean) {
        //获取商品分类
        HttpMethods.createService().getClassify("get_productclassification", channel.toString())
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<ClassifyBean>>() {
                    override fun onNext(t: HttpResult<ClassifyBean>) {
                        super.onNext(t)
                        mClassifyDatas.clear()
                        fragmentList.clear()
                        indexList.clear()

                        mClassifyAdapter = ClassifyAdapter(activity, mClassifyDatas)

                        val list = t.result.data
                        for (i in 0 until list.size) {
                            mClassifyDatas.add(list[i])
                            mIds.add(list[i].leveloneid)
                        }
                        mClassifyAdapter.notifyDataSetChanged()

                        //有多少分类，创建多少fragment
                        var fragment: BaseFragment
                        for (i in 0 until list.size) {
                            fragment = if (i == 0) {
                                RecommendFragment(activity as MainActivity, list[i])
                            } else {
                                ContentFragment(activity as MainActivity, list[i])
                            }
                            fragmentList.add(fragment)
                        }
                        if (needRefresh) {
                            //获取推荐列表
                            refreshContent(0)
                        }
                    }
                })
    }


    private fun refreshContent(position: Int) {
        val fragment = fragmentList[position]

        if (position == 0 && !isGetedRecommend) {
            (fragment as RecommendFragment).getHotRecommend()
            isGetedRecommend = true
        }
        replaceContent(fragment, R.id.fragmentContainer1)
        //设置数据
        if (position != 0) {
            if (!indexList.contains(position)) {
                Handler().postDelayed({
                    (fragmentList[position] as ContentFragment).setContentData(position, mClassifyDatas[position])
                }, 300)
                indexList.add(position)
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            val type = (activity as MainActivity).mChannel
            LogUtils.d("cha:" + type)

            if (preType != type) {
                //切换菜市，重新加载
                (activity as MainActivity).showProgressDialog()
                Handler().postDelayed({
                    getClassify(type, true)
                    isGetedRecommend = false
                    preType = type
                }, 1000)

            }
        }
    }
}
