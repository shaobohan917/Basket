package com.first.basket.utils

import android.content.Context
import android.widget.Toast

/**
 * Toast工具类
 * Created by Luka on 2016/10/4.
 */
object ToastUtil {
    private var mToast: Toast? = null

    /**
     * 显示提示信息
     *
     * @param context
     * @param text_resouce
     */
    fun showToast(context: Context, text_resouce: Int) {
        val text = context.resources.getString(text_resouce)
        showToast(context, text)
    }

    /**
     * 显示提示信息
     *
     * @param context
     * @param text
     */
    fun showToast(context: Context, text: String) {
        if (mToast != null) {
            mToast!!.cancel()
        }
        mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        mToast!!.show()
    }

    fun cancel() {
        if (mToast != null) {
            mToast!!.cancel()
        }
    }
}
