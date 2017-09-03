package com.first.basket.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by hanshaobo on 03/09/2017.
 */
class ImageUtils {
    companion object {
        fun loadUrl(context: Context, src: String, iv: ImageView) {
            Glide.with(context).load(src).into(iv)
        }
    }
}