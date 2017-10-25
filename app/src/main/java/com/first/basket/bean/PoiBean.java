package com.first.basket.bean;

import com.amap.api.services.core.PoiItem;

/**
 * Created by hanshaobo on 17/10/2017.
 */

public class PoiBean {

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public PoiItem getPoiItem() {
        return poiItem;
    }

    public void setPoiItem(PoiItem poiItem) {
        this.poiItem = poiItem;
    }

    private PoiItem poiItem;
    private boolean isCheck;
}

