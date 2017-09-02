package com.first.basket.adapter

import android.widget.TextView
import com.first.basket.R
import com.first.basket.bean.BaseBean
import com.first.basket.listener.OnItemClicklistener

/**
 * Created by hanshaobo on 02/09/2017.
 */
open class CategoryRecyclerViewAdapter(mDatas: List<BaseBean>, private val listener: OnItemClicklistener) : QuickAdapter<BaseBean>(mDatas) {

    override fun getLayoutId(viewType: Int): Int {

        return R.layout.item_recycler_category
    }

    override fun convert(holder: VH, data: BaseBean, position: Int) {
        holder.itemView.tag = mDatas
        var baseBean = mDatas[position]

        var tvCategory = holder.itemView.findViewById<TextView>(R.id.tvCategory)
        tvCategory.text = baseBean.category


        holder.itemView.setOnClickListener {
            listener.onItemClick(holder.itemView, position)
        }
    }

}