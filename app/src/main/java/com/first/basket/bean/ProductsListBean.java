package com.first.basket.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductsListBean implements Serializable{

    public ArrayList<ProductBean> data;

    public ArrayList<ProductBean> getData() {
        return data;
    }

    public void setData(ArrayList<ProductBean> data) {
        this.data = data;
    }
}
