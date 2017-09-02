package com.first.basket.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.first.basket.R

/**
 * Created by hanshaobo on 30/08/2017.
 */
class HomeView : LinearLayout {

    constructor(context: Context?) : super(context)

    init {
        var inflater = LayoutInflater.from(context).inflate(R.layout.frame_home, this)
    }


}