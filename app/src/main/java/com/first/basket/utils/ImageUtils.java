package com.first.basket.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.first.basket.R;

/**
 * Created by Luka on 2016/3/30.
 * E-mail:397308937@qq.com
 */
public class ImageUtils {
    private ImageUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void showImg(Context context, String uri, ImageView ivImg) {
        Glide.with(context).load(uri).into(ivImg);
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
