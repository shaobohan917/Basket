package com.first.basket.bean;

import com.amap.api.services.core.PoiItem;

import java.util.List;

/**
 * Created by hanshaobo on 17/10/2017.
 */

public class PoiBean {
    public List<PoiItem> getPoiItems() {
        return poiItems;
    }

    public void setPoiItems(List<PoiItem> poiItems) {
        this.poiItems = poiItems;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    private List<PoiItem> poiItems;
    private boolean isCheck;
}

