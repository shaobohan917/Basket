package com.first.basket.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.first.basket.R
import com.first.basket.constants.Constants

/**
 * Created by hanshaobo on 02/09/2017.
 */
class GoodsView(context: Context?) : FrameLayout(context) {
    private lateinit var mGoodsPic: String

    private lateinit var mGoodsName: String
    private lateinit var mGoodsNum: String
    private lateinit var mGoodsPrice: String

    private lateinit var ivGoods: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvNum: TextView
    private lateinit var tvPrice: TextView

    constructor(context: Context?, attrs: AttributeSet) : this(context) {
        var typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.GoodsView)
        for (i in 0..typedArray.indexCount) {
            var attr = typedArray.getIndex(i)
            when (attr) {
//                R.styleable.GoodsView_goodsPic ->
//                    mGoodsPic = Constants.PIC_URL
//                    mGoodsPic = typedArray.getString(attr)
                R.styleable.GoodsView_goodsName ->
                    mGoodsName = typedArray.getString(attr)
                R.styleable.GoodsView_goodsNum ->
                    mGoodsNum = typedArray.getString(attr)
                R.styleable.GoodsView_goodsPrice ->
                    mGoodsPrice = typedArray.getString(attr)
            }
        }
        typedArray.recycle()

        LayoutInflater.from(context).inflate(R.layout.layout_view_goods, this)
        initView()

    }

    private fun initView() {
        ivGoods = findViewById(R.id.ivGoods)
        tvName = findViewById(R.id.tvName)
        tvNum = findViewById(R.id.tvNum)
        tvPrice = findViewById(R.id.tvPrice)

        setGoods( mGoodsName, mGoodsNum, mGoodsPrice)
    }

    private fun setGoods(name: String, num: String, price: String) {
//        Glide.with(this).load(pic).into(ivGoods)
        tvName.text = name
        tvNum.text = num
        tvPrice.text = price
    }
}