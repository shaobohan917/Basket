package com.first.basket.utils;

import android.content.Context;
import android.os.Handler;

import com.first.basket.app.BaseApplication;

/**
 * Created by hanshaobo on 15/10/2017.
 */

public class UIUtils {
    public static Context getContext() {
        return BaseApplication.getContext();
    }

    // 判断是否是主线的方法
    public static boolean isRunInMainThread() {
        return getMainThreadId() == android.os.Process.myTid();
    }

    // 保证当前的UI操作在主线程里面运行
    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            // 如果现在就是在主线程中，就直接运行run方法
            runnable.run();
        } else {
            // 否则将其传到主线程中运行
            getHandler().post(runnable);
        }
    }

    public static int getMainThreadId() {
        return BaseApplication.getMainThreadId();
    }


    public static Handler getHandler() {
        return BaseApplication.getHandler();
    }

}
