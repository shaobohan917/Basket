package com.first.basket.adapter

import android.widget.TextView
import com.first.basket.R
import com.first.basket.bean.BaseBean
import com.first.basket.bean.CategoryContentBean
import com.first.basket.listener.OnItemClicklistener

/**
 * Created by hanshaobo on 02/09/2017.
 */
open class ContentRecyclerViewAdapter(mDatas: List<CategoryContentBean>) : QuickAdapter<CategoryContentBean>(mDatas) {

    override fun getLayoutId(viewType: Int): Int {

        return R.layout.item_recycler_content
    }

    override fun convert(holder: VH, data: CategoryContentBean, position: Int) {
        holder.itemView.tag = mDatas
        var bean = mDatas[position]

        var tvName = holder.itemView.findViewById<TextView>(R.id.tvName)
        var tvNum = holder.itemView.findViewById<TextView>(R.id.tvNum)
        var tvPrice = holder.itemView.findViewById<TextView>(R.id.tvPrice)
        tvName.text = bean.name
        tvNum.text = "" + bean.num
        tvPrice.text = "" + bean.price

    }

}