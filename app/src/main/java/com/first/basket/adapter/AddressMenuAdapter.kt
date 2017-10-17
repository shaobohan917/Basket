package com.first.basket.adapter

import android.annotation.TargetApi
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.first.basket.R
import com.first.basket.bean.AddressBean
import com.first.basket.common.CommonMethod
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by hanshaobo on 14/09/2017.
 */
class AddressMenuAdapter(list: ArrayList<AddressBean>, listener: OnItemClickListener, cbListener: OnItemCheckedListener) : SwipeMenuAdapter<AddressMenuAdapter.ViewHolder>() {
    private var mDatas: ArrayList<AddressBean> = list
    private var listener = listener
    private var cbListener = cbListener

    override fun getItemCount(): Int {
        return mDatas.size
    }

    override fun onCreateContentView(parent: ViewGroup, viewType: Int): View? {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_address, parent, false);
    }

    override fun onCompatCreateViewHolder(realContentView: View, viewType: Int): ViewHolder? {
        return ViewHolder(realContentView)
    }

    override fun onBindViewHolder(holder: AddressMenuAdapter.ViewHolder, position: Int) {
        holder.itemView.tag = mDatas[position]

        val bean = mDatas[position]

        holder.tvName.text = bean.receiver
        holder.tvPhone.text = bean.recvphone
        holder.tvAddress.text = bean.street

        holder.cbDefault.isChecked = CommonMethod.isTrue(mDatas[position].defaultaddr)
        holder.cbDefault.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                cbListener.onItemChecked(mDatas[position].addressid)
            }
        }
        holder.ivDelete.visibility = View.GONE
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName = itemView.findViewById<TextView>(R.id.tvName)
        var tvPhone = itemView.findViewById<TextView>(R.id.tvPhone)
        var tvAddress = itemView.findViewById<TextView>(R.id.tvAddress)

        var cbDefault = itemView.findViewById<CheckBox>(R.id.cbDefault)
        var ivModify = itemView.findViewById<ImageView>(R.id.ivModify)
        var ivDelete = itemView.findViewById<ImageView>(R.id.ivDelete)

        init {
            ivModify.onClick {
                listener.onItemClick(itemView, position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    interface OnItemCheckedListener {
        //        fun onItemChecked(view: View, isChecked: Boolean, position: Int)
        fun onItemChecked(addressid: String)
    }
}