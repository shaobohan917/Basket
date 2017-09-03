package com.first.basket.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.first.basket.R
import com.first.basket.bean.BaseBean
import com.first.basket.bean.CategoryContentBean
import com.first.basket.listener.OnItemClicklistener

/**
 * Created by hanshaobo on 02/09/2017.
 */
open class ContentRecyclerViewAdapter(mDatas: List<CategoryContentBean>) : QuickAdapter<CategoryContentBean>(mDatas) {
    var count: Int = 1
    override fun getLayoutId(viewType: Int): Int {

        return R.layout.item_recycler_content
    }

    override fun convert(holder: VH, data: CategoryContentBean, position: Int) {
        holder.itemView.tag = mDatas
        var bean = mDatas[position]

        var tvName = holder.itemView.findViewById<TextView>(R.id.tvName)
        var tvNum = holder.itemView.findViewById<TextView>(R.id.tvNum)
        var tvPrice = holder.itemView.findViewById<TextView>(R.id.tvPrice)
        var tvUnit = holder.itemView.findViewById<TextView>(R.id.tvUnit)
        var ivAdd = holder.itemView.findViewById<ImageView>(R.id.ivAdd)
        tvName.text = bean.name
        tvUnit.text = "" + bean.num
        tvPrice.text = "" + bean.price

        ivAdd.setOnClickListener {
            tvNum.visibility = View.VISIBLE
            tvNum.text = count++.toString()

        }


    }

}