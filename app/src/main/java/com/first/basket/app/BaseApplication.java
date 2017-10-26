package com.first.basket.app;

import android.os.Handler;
import android.support.multidex.MultiDexApplication;

import com.first.basket.R;
import com.first.basket.bean.ProductBean;
import com.first.basket.utils.SPUtil;
import com.tencent.bugly.Bugly;

import java.util.ArrayList;

/**
 * Created by hanshaobo on 15/10/2017.
 */

public class BaseApplication extends MultiDexApplication {
    private static int mMainThreadId;// 主线程Id
    private static Handler mHandler;// Handler对象

    private static BaseApplication instance;
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

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
        BaseApplication.setMainThreadId(android.os.Process.myTid());
        BaseApplication.setHandler(new Handler());

        //初始化sp
        SPUtil.init(this);
        Bugly.init(getApplicationContext(), getString(R.string.bugly), true);
    }

    public static BaseApplication getInstance() {
        return instance;
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
