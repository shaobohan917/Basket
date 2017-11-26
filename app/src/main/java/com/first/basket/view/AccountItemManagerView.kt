package com.first.basket.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

import com.first.basket.R
import kotlinx.android.synthetic.main.layout_widget_account_manager.view.*

/**
 * Created by hanshaobo on 16/9/12.
 */
class AccountItemManagerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    private var mImgId: Int = 0
    private var mTitle: String? = null
    private var mRight: String = ""

    init {

        //获取自定义属性
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.accountItemManagerView, defStyleAttr, 0)
        val count = typedArray.indexCount
        for (i in 0..count - 1) {
            val attr = typedArray.getIndex(i)
            when (attr) {
                R.styleable.accountItemManagerView_accountManagerImg -> mImgId = typedArray.getResourceId(attr, defStyleAttr)
                R.styleable.accountItemManagerView_accountManagerTitle -> mTitle = typedArray.getString(attr)
                R.styleable.accountItemManagerView_accountManagerRight -> mRight = typedArray.getString(attr)
            }
        }
        typedArray.recycle()

        LayoutInflater.from(context).inflate(R.layout.layout_widget_account_manager, this)
        initView()
    }

    private fun initView() {
        val ivImg = findViewById<ImageView>(R.id.ivImg) as ImageView
        val tvTitle = findViewById<TextView>(R.id.tvTitle) as TextView
        val tvRight = findViewById<TextView>(R.id.tvRight) as TextView

        tvTitle.text = mTitle
        ivImg.setImageResource(mImgId)
        tvRight.text = mRight
    }

    fun setRightText(str: String) {
        tvRight.text = str
    }
}
