package com.first.basket.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.first.basket.R
import com.first.basket.base.BaseActivity
import kotlinx.android.synthetic.main.layout_widget_title.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by hanshaobo on 02/09/2017.
 */
class TitleView : FrameLayout, View.OnClickListener {

    private lateinit var mTitleText: String
    private var mTitleColor: Int = 0
    private var mTitleBackgroundColor = 0

    private lateinit var tvTitle: TextView
    private lateinit var rlyt_title: RelativeLayout

    private var mIsShowBack: Boolean = true

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        var typedArray = context!!.obtainStyledAttributes(attrs, R.styleable.TitleView)
        for (i in 0..typedArray.indexCount) {
            var attr = typedArray.getIndex(i)
            when (attr) {
                R.styleable.TitleView_titleText ->
                    mTitleText = typedArray.getString(attr)
                R.styleable.TitleView_titleBackground ->
                    mTitleBackgroundColor = typedArray.getColor(attr, context.resources.getColor(R.color.white))
                R.styleable.TitleView_titleColor ->
                    mTitleColor = typedArray.getColor(attr, context.resources.getColor(R.color.gray33))
                R.styleable.TitleView_showBack ->
                    mIsShowBack = typedArray.getBoolean(attr, false)
            }
        }
        typedArray.recycle()

        LayoutInflater.from(context).inflate(R.layout.layout_widget_title, this)
        initView()
    }


    private fun initView() {
        tvTitle = findViewById(R.id.tvTitle)
        rlyt_title = findViewById(R.id.rlyt_title)
        tvTitle.text = mTitleText
        tvTitle.setTextColor(mTitleColor)
        rlyt_title.setBackgroundColor(mTitleBackgroundColor)


        ivBack.onClick {
            (getContext() as BaseActivity).myFinish()
        }
        ivBack.visibility = if (mIsShowBack) View.VISIBLE else View.GONE

    }

    override fun onClick(p0: View?) {


    }

    fun setTitle(string: String) {
        if (!TextUtils.isEmpty(string)) {
            tvTitle.text = string
        }
    }
}