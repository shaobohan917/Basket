package com.first.basket.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.first.basket.R;
import com.first.basket.bean.ProductsBean;
import com.first.basket.utils.SPUtil;
import com.tencent.bugly.crashreport.CrashReport;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by hanshaobo on 15/10/2017.
 */

public class BaseApplication extends Application {
    private static int mMainThreadId;// 主线程Id
    private static Handler mHandler;// Handler对象

    private static Context context;

    private static BaseApplication instance;

    public ArrayList<ProductsBean> mProductBean = new ArrayList<>();
    public LinkedHashMap<ProductsBean, Integer> mGoodsMap = new LinkedHashMap();   //添加到购物车的集合

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        this.instance = this;
        BaseApplication.setMainThreadId(android.os.Process.myTid());
        BaseApplication.setHandler(new Handler());

        //初始化sp
        SPUtil.init(this);
        CrashReport.initCrashReport(getApplicationContext(), getString(R.string.bugly), true);
    }

    public static BaseApplication getInstance() {
        return instance;
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
