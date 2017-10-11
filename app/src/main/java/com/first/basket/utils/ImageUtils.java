package com.first.basket.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.first.basket.R;
import com.first.basket.constants.Constants;

/**
 * Created by Luka on 2016/3/30.
 * E-mail:397308937@qq.com
 */
public class ImageUtils {
    public static void showImg(Context context, String url, ImageView ivImg) {
        if (!url.startsWith(Constants.Companion.getBASE_IMG_URL())) {
            url = Constants.Companion.getBASE_IMG_URL() + url;
        }
        Glide.with(context).load(url).into(ivImg);
        LogUtils.Companion.d("url:" + url);

    }

    public static void showImg(Context context, int resourceId, ImageView ivImg) {
        Glide.with(context).load(resourceId).into(ivImg);
    }

    public static void showImg(Context context, Uri uri, ImageView ivImg) {
        Glide.with(context).load(uri).into(ivImg);
    }

    public static void showImg(Context context, Bitmap bitmap, ImageView ivImg) {
        Glide.with(context).load(bitmap).into(ivImg);
    }
}