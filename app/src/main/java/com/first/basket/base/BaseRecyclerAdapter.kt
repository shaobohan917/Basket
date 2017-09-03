package com.first.basket.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Created by hanshaobo on 03/09/2017.
 */
class BaseRecyclerAdapter<T, K : BaseRecyclerAdapter.ViewHolder<T>>(val layoutResourseId: Int, val items: List<T>, val init: (View, T) -> Unit) : RecyclerView.Adapter<K>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): K {
        var inflater = parent!!.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(layoutResourseId, parent, false)
        return ViewHolder<T>(view, init) as K
    }

    override fun onBindViewHolder(holder: K, position: Int) {
        holder.bindForecast(items[position])
        holder.itemView.tag = position
    }

    override fun getItemCount() = items.size

    class ViewHolder<in T>(view: View, val init: (View, T) -> Unit) : RecyclerView.ViewHolder(view) {
        fun bindForecast(item: T) {
            with(item) {
                init(itemView, item)
            }
        }
    }
}