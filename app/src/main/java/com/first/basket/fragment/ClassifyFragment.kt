package com.first.basket.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.first.basket.R
import com.first.basket.activity.MainActivity
import com.first.basket.activity.SearchListActivity
import com.first.basket.adapter.ClassifyAdapter
import com.first.basket.base.HttpResult
import com.first.basket.bean.ClassifyBean
import com.first.basket.common.CommonMethod
import com.first.basket.common.StaticValue
import com.first.basket.http.HttpMethods
import com.first.basket.http.HttpResultSubscriber
import com.first.basket.http.TransformUtils
import com.first.basket.utils.LogUtils
import com.first.basket.utils.SPUtil
import kotlinx.android.synthetic.main.fragment_classify.*
import kotlinx.android.synthetic.main.item_et_search.*
import java.lang.Exception

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
        initView()
        initData()
        initListener()
    }

    private fun initView() {
        val drawable = activity.resources.getDrawable(R.mipmap.ic_category_search)
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        etSearch.setCompoundDrawables(drawable, null, null, null)

        classifyRecyclerView.layoutManager = LinearLayoutManager(activity)
    }


    private fun initData() {
        preType = (activity as MainActivity).mChannel
        //初始化分类列表adapter
        mClassifyAdapter = ClassifyAdapter(activity, mClassifyDatas)
        classifyRecyclerView.adapter = mClassifyAdapter
        //获取分类列表
        getClassify(preType, true)
    }

    private fun initListener() {
        etSearch.setOnEditorActionListener { p0, p1, p2 ->
            if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                CommonMethod.hideKeyboard(etSearch)
                goSearchList(p0.text.toString())
            }
            false
        }

        mClassifyAdapter.setOnItemClickListener { view, data, position ->
            refreshContent(position)
        }
    }

    private fun goSearchList(str: String) {
        var intent = Intent(activity, SearchListActivity::class.java)
        intent.putExtra("search", str)
        startActivity(intent)
    }

    private fun getClassify(channel: Int, needRefresh: Boolean) {
        //获取商品分类
        HttpMethods.createService().getClassify("get_productclassification", channel.toString())
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(object : HttpResultSubscriber<HttpResult<ClassifyBean>>() {
                    override fun onNext(t: HttpResult<ClassifyBean>) {
                        super.onNext(t)
                        setClassify(t)
                        if (needRefresh) {
                            //获取推荐列表
                            refreshContent(0)

                            classifyRecyclerView.smoothScrollToPosition(0)
                            Handler().postDelayed({
                                try {
                                    var holder = classifyRecyclerView.getChildViewHolder(classifyRecyclerView.getChildAt(0)) as ClassifyAdapter.MyViewHolder
                                    holder.itemView.performClick()
                                } catch (e: Exception) {
                                    LogUtils.d("捕获：" + e.message)
                                }
                            }, 300)

                        }
                    }

                    override fun onCompleted() {
                        super.onCompleted()
                        (activity as MainActivity).hideLoading()
                    }
                })
    }


    private fun setClassify(t: HttpResult<ClassifyBean>) {
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
            fragment = ContentFragment(activity as MainActivity, list[i])
            fragmentList.add(fragment)
        }
    }


    private fun refreshContent(position: Int) {
        val fragment = fragmentList[position] as ContentFragment

        if (position == 0) {
            if (!isGetedRecommend) {
                fragment.getHotRecommend(activity as MainActivity)
                isGetedRecommend = true
            } else {
                Handler().postDelayed({
                    fragment.showHotImg(true)
                }, 100)
            }
        } else {
            if (!indexList.contains(position)) {
                Handler().postDelayed({
                    fragment.setContentData(position, mClassifyDatas[position])
                }, 300)
                indexList.add(position)
            }
        }
        fragmentList[position] = fragment

        replaceContent(fragment, R.id.fragmentContainer1)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            val type = (activity as MainActivity).mChannel

            if (preType != type) {
                //切换菜市，重新加载
                (activity as MainActivity).showLoading()
                Handler().postDelayed({
                    getClassify(type, true)
                    isGetedRecommend = false
                    preType = type
                }, 1000)
            }
        }
    }
}
