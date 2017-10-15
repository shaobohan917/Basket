package com.first.basket.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.first.basket.utils.SPUtil;
import com.tencent.bugly.crashreport.CrashReport;

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

        //初始化sp
        SPUtil.init(this);
        CrashReport.initCrashReport(getApplicationContext(), "8fab8931-4dd4-4578-9e36-91e55992e34d", true);
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
