package com.first.basket.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.first.basket.R;
import com.first.basket.bean.ProductBean;
import com.first.basket.utils.SPUtil;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.loader.app.DefaultApplicationLike;

import java.util.ArrayList;

/**
 * Created by hanshaobo on 2018/5/7.
 */

public class SampleApplicationLike extends DefaultApplicationLike {

    public static final String TAG = "Tinker.SampleApplicationLike";

    public SampleApplicationLike(Application application, int tinkerFlags,
                                 boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
                                 long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        Bugly.init(getApplication(), getApplication().getString(R.string.bugly), true);

        this.instance = this;
        setMainThreadId(android.os.Process.myTid());
        setHandler(new Handler());

        //初始化sp
        SPUtil.init(getApplication());

    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }



    //项目本身的逻辑
    private static int mMainThreadId;// 主线程Id
    private static Handler mHandler;// Handler对象

    private static SampleApplicationLike instance;
    private ArrayList<ProductBean> mProductsList = new ArrayList<>();

    //获取列表
    public ArrayList<ProductBean> getProductsList() {
        return mProductsList;
    }

    public void setProductsList(ArrayList<ProductBean> mProductsList) {
        this.mProductsList = mProductsList;
    }

    //获取数目
    public int getProductsCount() {
        int count = 0;
        for (int i = 0; i < mProductsList.size(); i++) {
            count += mProductsList.get(i).getAmount();
        }
        return count;
    }

    /**
     * 获取所有选中的商品
     *
     * @return
     */
    public ArrayList<ProductBean> getIsCheckProducts() {
        ArrayList<ProductBean> list = new ArrayList<ProductBean>();
        for (int i = 0; i < mProductsList.size(); i++) {
            if (mProductsList.get(i).getIsCheck()) {
                list.add(mProductsList.get(i));
            }
        }
        return list;
    }

    public void addProduct(ProductBean productBean) {
        productBean.setIsCheck(true);
        String id = productBean.getProductid();
        ArrayList<String> ids = new ArrayList<>();

        for (int i = 0; i < mProductsList.size(); i++) {
            ids.add(mProductsList.get(i).getProductid());
        }
        if (ids.contains(id)) {
            int index = ids.indexOf(id);
            mProductsList.get(index).setAmount(mProductsList.get(index).getAmount() + 1);
        } else {
            productBean.setAmount(1);
            mProductsList.add(productBean);
        }
    }

    public static SampleApplicationLike getInstance() {
        return instance;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }

    public static void setMainThreadId(int mMainThreadId) {
        SampleApplicationLike.mMainThreadId = mMainThreadId;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static void setHandler(Handler mHandler) {
        SampleApplicationLike.mHandler = mHandler;
    }
}
