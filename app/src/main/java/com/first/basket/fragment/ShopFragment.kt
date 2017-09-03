package com.first.basket.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.first.basket.R
import com.first.basket.adapter.ShopAdapter
import com.first.basket.bean.ShopBean
import com.first.basket.utils.LogUtils
import com.first.basket.view.GoodsView
import com.first.basket.view.TitleView

/**
 * Created by hanshaobo on 30/08/2017.
 */
class ShopFragment : BaseFragment() {
    private var mDatas = ArrayList<ShopBean>()
    private var contents = arrayOf("奇异果", "香蕉", "苹果")

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_shop, container, false)!!
        initView(view)
        return view
    }

    private fun initView(view: View) {
        var gvGoods = view.findViewById<GoodsView>(R.id.gvGoods)
        LogUtils.d("gv:" + gvGoods)
//        gvGoods.setOnClickListener { startActivity(Intent(activity, GoodsDetailActivity::class.java)) }
        view.findViewById<TitleView>(R.id.titleView)
        var recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        for (j in contents) {
            var bean = ShopBean()
            bean.name = j
            bean.num = 1
            bean.price = 1f
            mDatas.add(bean)
        }
        recyclerView.adapter = ShopAdapter(mDatas)


    }
}