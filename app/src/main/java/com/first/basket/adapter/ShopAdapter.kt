package com.first.basket.adapter

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.first.basket.R
import com.first.basket.app.BaseApplication
import com.first.basket.bean.AddressBean
import com.first.basket.bean.ProductBean
import com.first.basket.common.CommonMethod
import com.first.basket.common.StaticValue
import com.first.basket.utils.ImageUtils
import com.first.basket.utils.SPUtil
import com.first.basket.view.AmountView
import com.google.gson.GsonBuilder
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by hanshaobo on 14/09/2017.
 */
class MenuAdapter(list: ArrayList<ProductBean>, listener: OnItemClickListener, cbListener: OnItemCheckedListener, amountListener: OnItemAmountChangedListener) : SwipeMenuAdapter<MenuAdapter.ViewHolder>() {
    private var mDatas: ArrayList<ProductBean> = list
    private var listener = listener
    private var cbListener = cbListener
    private var amountListener = amountListener
    var isModifyMode = false

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

        val product = mDatas[position]

        holder.tvName1.text = product.productname

        holder.tvUnit1.text = product.weight + "/" + product.unit
        holder.tvPrice1.text = product.cost
        holder.amoutView.amount = product.amount
        ImageUtils.showImg(BaseApplication.getInstance(), product.img, holder.ivGoods)
        holder.cbSelect.isChecked = product.isCheck
        holder.cbSelect.setOnCheckedChangeListener { compoundButton, b ->
            product.isCheck = b
            cbListener.onItemCheck(compoundButton, b, position)
        }
        holder.amoutView.setOnAmountChangeListener { view, amount ->
            amountListener.onItemAmountChanged(view, amount, position)
        }
        holder.itemView.onClick { holder.cbSelect.performClick() }

        if (position == 0) {
            holder.tvTitle.visibility = View.VISIBLE
            setTitle(holder.tvTitle, mDatas[0].channelid)
        } else {
            if (mDatas.get(position - 1).channelid == mDatas[position].channelid) {
                //相同，不展示
                holder.tvTitle.visibility = View.GONE
            } else {
                holder.tvTitle.visibility = View.VISIBLE
                setTitle(holder.tvTitle, mDatas[position].channelid)
            }
        }
        if (isModifyMode) {
            holder.llShadow.visibility = View.GONE
        } else {
            checkStatus(holder, position)
        }
    }

    private fun checkStatus(holder: ViewHolder, position: Int) {
        //判断菜市是否可选
        val str = SPUtil.getString(StaticValue.DEFAULT_ADDRESS, "")
        if (!TextUtils.isEmpty(str)) {
            val gson = GsonBuilder().create()
            val addressInfo = gson.fromJson(str, AddressBean::class.java)

            when {
                mDatas[position].channelid == "1" -> holder.llShadow.visibility = if (CommonMethod.isTrue(addressInfo.issqcs)) (View.GONE) else (View.VISIBLE)
                mDatas[position].channelid == "2" -> holder.llShadow.visibility = if (CommonMethod.isTrue(addressInfo.isshcs)) (View.GONE) else (View.VISIBLE)
                mDatas[position].channelid == "3" -> holder.llShadow.visibility = if (CommonMethod.isTrue(addressInfo.isqgcs)) (View.GONE) else (View.VISIBLE)
            }
        }

    }

    fun setTitle(tv: TextView, str: String) {
        when (str) {
            "1" -> tv.text = "社区菜市"
            "2" -> tv.text = "上海菜市"
            "3" -> tv.text = "全国菜市"
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        var tvName1 = itemView.findViewById<TextView>(R.id.tvName1)
        var tvUnit1 = itemView.findViewById<TextView>(R.id.tvUnit1)
        var tvPrice1 = itemView.findViewById<TextView>(R.id.tvPrice1)
        var amoutView = itemView.findViewById<AmountView>(R.id.amoutView)!!
        var ivGoods = itemView.findViewById<ImageView>(R.id.ivGoods)
        var cbSelect = itemView.findViewById<CheckBox>(R.id.cbSelect)
        var llShadow = itemView.findViewById<LinearLayout>(R.id.llShadow)

        init {
            itemView.onClick { listener.onItemClick(itemView) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View)
    }

    interface OnItemCheckedListener {
        fun onItemCheck(view: View, b: Boolean, index: Int)
    }

    interface OnItemAmountChangedListener {
        fun onItemAmountChanged(view: View, amount: Int, index: Int)
    }

    fun setIsModifyMode(b: Boolean) {
        isModifyMode = b
        notifyDataSetChanged()
    }
}