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

        holder.etName.text = bean.receiver
        holder.etPhone.text = bean.recvphone
        holder.etAddress.text = bean.street

        holder.cbDefault.isChecked = CommonMethod.isTrue(mDatas[position].defaultaddr)
        holder.cbDefault.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                cbListener.onItemChecked(mDatas[position].addressid)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var etName = itemView.findViewById<TextView>(R.id.etName)
        var etPhone = itemView.findViewById<TextView>(R.id.etPhone)
        var etAddress = itemView.findViewById<TextView>(R.id.etAddress)

        var cbDefault = itemView.findViewById<CheckBox>(R.id.cbDefault)
        var ivModify = itemView.findViewById<ImageView>(R.id.ivModify)

        init {
            ivModify.onClick {
                listener.onItemClick(itemView, position)
                setModifiable(true)
            }

            setModifiable(false)
        }

        fun setModifiable(modifiable: Boolean) {
            etName.isEnabled = modifiable
            etPhone.isEnabled = modifiable
            etAddress.isEnabled = modifiable

            if (modifiable) {

            } else {
                etName.background = null
                etAddress.background = null
                etPhone.background = null
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

//    fun setModifiable(holder: AddressMenuAdapter.ViewHolder,modifiable:Boolean){
//        holder.etName.isClickable = modifiable
//        holder.etAddress.isClickable = modifiable
//        holder.etPhone.isClickable = modifiable
//    }
}