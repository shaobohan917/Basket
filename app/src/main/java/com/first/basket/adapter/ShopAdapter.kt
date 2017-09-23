package com.first.basket.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.first.basket.R
import com.first.basket.bean.GoodsDetailBean
import com.first.basket.view.AmountView
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hanshaobo on 14/09/2017.
 */
class MenuAdapter(list: ArrayList<GoodsDetailBean.ResultBean.DataBean>, listener: OnItemClickListener) : SwipeMenuAdapter<MenuAdapter.ViewHolder>() {

    private var mDatas: ArrayList<GoodsDetailBean.ResultBean.DataBean> = list
    private var listener = listener

    override fun getItemCount(): Int {
        return mDatas.size
    }

    override fun onCreateContentView(parent: ViewGroup, viewType: Int): View? {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_shop, parent, false);
    }

    override fun onCompatCreateViewHolder(realContentView: View, viewType: Int): ViewHolder? {
        return ViewHolder(realContentView)
    }

    override fun onBindViewHolder(holder: MenuAdapter.ViewHolder, position: Int) {
        holder.itemView.tag = mDatas[position]

        val bean = mDatas[position]

        holder.tvName1.text = bean.title
        holder.tvUnit1.text = bean.subtitle
        holder.tvPrice1.text = bean.price.toString()
        holder.amoutView.setGoods_storage(10)


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName1 = itemView.findViewById<TextView>(R.id.tvName1)
        var tvUnit1 = itemView.findViewById<TextView>(R.id.tvUnit1)
        var tvPrice1 = itemView.findViewById<TextView>(R.id.tvPrice1)
        var amoutView = itemView.findViewById<AmountView>(R.id.amoutView)

        init {
            itemView.onClick { listener.onItemClick(itemView) }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(view: View)
    }
}