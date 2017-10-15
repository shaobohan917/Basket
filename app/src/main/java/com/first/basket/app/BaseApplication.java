package com.first.basket.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by hanshaobo on 15/10/2017.
 */

public class BaseApplication extends Application {
    private static int mMainThreadId;// 主线程Id
    private static Handler mHandler;// Handler对象

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        BaseApplication.setMainThreadId(android.os.Process.myTid());
        BaseApplication.setHandler(new Handler());
    }

    public static Context getContext() {
        return context;
    }


    public static int getMainThreadId() {
        return mMainThreadId;
    }

    public static void setMainThreadId(int mMainThreadId) {
        BaseApplication.mMainThreadId = mMainThreadId;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static void setHandler(Handler mHandler) {
        BaseApplication.mHandler = mHandler;
    }
}
