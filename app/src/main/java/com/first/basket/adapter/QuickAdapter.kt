package com.first.basket.adapter

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
* Created by hanshaobo on 06/02/2017.
*/

abstract class QuickAdapter<T>(val mDatas: List<T>) : RecyclerView.Adapter<QuickAdapter.VH>() {
    var TAG = "cmlog"

    abstract fun getLayoutId(viewType: Int): Int

    abstract fun convert(holder: VH, data: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH[parent, getLayoutId(viewType)]
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        convert(holder, mDatas[position], position)

    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    class VH(private val mConvertView: View) : RecyclerView.ViewHolder(mConvertView) {
        private val mViews: SparseArray<View>

        init {
            mViews = SparseArray()
        }

        fun <T : View> getView(id: Int): T {
            var v: View? = mViews.get(id)
            if (v == null) {
                v = mConvertView.findViewById(id)
                mViews.put(id, v)
            }
            return v as T
        }

        fun setView(id: Int) {}

        companion object {

            operator fun get(parent: ViewGroup, layoutId: Int): VH {
                val convertView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
                return VH(convertView)
            }
        }

        //        public void setText(int id, String value) {
        //            TextView view = getView(id);
        //            view.setText(value);
        //        }
    }


}
