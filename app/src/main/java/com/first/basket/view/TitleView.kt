package com.first.basket.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.first.basket.R

/**
 * Created by hanshaobo on 02/09/2017.
 */
class TitleView(context: Context?) : FrameLayout(context), View.OnClickListener {

    private lateinit var mTitleText: String
//    private lateinit var mTitleColor: Int
    private lateinit var tvTitle: TextView

    constructor(context: Context?, attrs: AttributeSet) : this(context) {
        var typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.TitleView)
        for (i in 0..typedArray.indexCount) {
            var attr = typedArray.getIndex(i)
            when (attr) {
                R.styleable.TitleView_titleText ->
                    mTitleText = typedArray.getString(attr)
//                R.styleable.TitleView_titleColor ->
//                    mTitleColor = typedArray.getColor(attr, defStyleAttr)
            }
        }
        typedArray.recycle()

        LayoutInflater.from(context).inflate(R.layout.layout_widget_title, this)
        initView()
    }

    private fun initView() {
        tvTitle = findViewById(R.id.tvTitle)
        tvTitle.text = mTitleText

    }

    override fun onClick(p0: View?) {


    }
}