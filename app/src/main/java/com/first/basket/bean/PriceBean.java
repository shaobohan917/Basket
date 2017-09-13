package com.first.basket.bean;

/**
 * Created by hanshaobo on 13/09/2017.
 */

public class PriceBean {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private float totalprice;
        private float totalcost;

        public float getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(float totalprice) {
            this.totalprice = totalprice;
        }

        public float getTotalcost() {
            return totalcost;
        }

        public void setTotalcost(float totalcost) {
            this.totalcost = totalcost;
        }
    }
}
