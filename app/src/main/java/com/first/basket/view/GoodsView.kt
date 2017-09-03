package com.first.basket.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.first.basket.R

/**
 * Created by hanshaobo on 02/09/2017.
 */
//class GoodsView(context: Context, attrs: AttributeSet?, defStyleAttr: Int)  : FrameLayout(context,attrs,defStyleAttr) {
class GoodsView : FrameLayout {
    private var mGoodsPic: Int = 0
    private lateinit var mGoodsName: String
    private lateinit var mGoodsNum: String
    private lateinit var mGoodsPrice: String
    private var mGoodsPicHeight: Float = 100f

    private lateinit var ivGoods: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvNum: TextView
    private lateinit var tvPrice: TextView

    constructor(context: Context) : this(context, attrs = null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        var typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.GoodsView)
        (0..typedArray.indexCount)
                .asSequence()
                .map { typedArray.getIndex(it) }
                .forEach {
                    when (it) {
                        R.styleable.GoodsView_goodsPic ->
                            mGoodsPic = typedArray.getResourceId(it, 0)
                        R.styleable.GoodsView_goodsName ->
                            mGoodsName = typedArray.getString(it)
                        R.styleable.GoodsView_goodsNum ->
                            mGoodsNum = typedArray.getString(it)
                        R.styleable.GoodsView_goodsPrice ->
                            mGoodsPrice = typedArray.getString(it)
                        R.styleable.GoodsView_goodsPicHeight ->
                            mGoodsPicHeight = typedArray.getDimension(it, 100f)
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

        tvName.text = mGoodsName
        tvNum.text = mGoodsNum
        tvPrice.text = mGoodsPrice
        val params = ivGoods.layoutParams
        params.height = mGoodsPicHeight.toInt()
        ivGoods.layoutParams = params
        Glide.with(context).load(mGoodsPic).into(ivGoods)
    }

    private fun setGoods(name: String, num: String, price: String) {
        tvName.text = name
        tvNum.text = num
        tvPrice.text = price
    }
}