package com.first.basket.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanshaobo on 26/11/2017.
 */

public class OrderListBean implements Serializable{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * orderid : 8000302
         * userid : 6003001
         * strorderid : 170350371511674379
         * addressid : 7002731
         * delievedt : 2017-11-27
         * delievetime : 上午
         * delieveweekday :
         * qty : 1
         * cost : 0.00
         * price : 19.50
         * fare : 30.00
         * donateAXWX : 0.19
         * donateYBBL : 0.09
         * pay : 49.50
         * customizedonatename :
         * customizedonatefee : 0.00
         * orderdt : 2017-11-26 13:32:59
         * paymentid : 4
         * statusid : 3
         * updatedt : 2017-11-26 13:32:59
         * orderdetail : [{"ordersid":"8000302","productid":"10001023","number":"1","cost":"0.00","price":"19.50","productname":"中粮艾谷 有机白芸豆","weight":"330g","unit":"袋","img":"/prod_pic/105/10001023.png"}]
         */

        private String orderid;
        private String userid;
        private String strorderid;
        private String addressid;
        private String delievedt;
        private String delievetime;
        private String delieveweekday;
        private String qty;
        private String cost;
        private String price;
        private String fare;
        private String donateAXWX;
        private String donateYBBL;
        private String pay;
        private String customizedonatename;
        private String customizedonatefee;
        private String orderdt;
        private String paymentid;
        private String statusid;
        private String updatedt;
        private ArrayList<ProductBean> orderdetail;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getStrorderid() {
            return strorderid;
        }

        public void setStrorderid(String strorderid) {
            this.strorderid = strorderid;
        }

        public String getAddressid() {
            return addressid;
        }

        public void setAddressid(String addressid) {
            this.addressid = addressid;
        }

        public String getDelievedt() {
            return delievedt;
        }

        public void setDelievedt(String delievedt) {
            this.delievedt = delievedt;
        }

        public String getDelievetime() {
            return delievetime;
        }

        public void setDelievetime(String delievetime) {
            this.delievetime = delievetime;
        }

        public String getDelieveweekday() {
            return delieveweekday;
        }

        public void setDelieveweekday(String delieveweekday) {
            this.delieveweekday = delieveweekday;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getFare() {
            return fare;
        }

        public void setFare(String fare) {
            this.fare = fare;
        }

        public String getDonateAXWX() {
            return donateAXWX;
        }

        public void setDonateAXWX(String donateAXWX) {
            this.donateAXWX = donateAXWX;
        }

        public String getDonateYBBL() {
            return donateYBBL;
        }

        public void setDonateYBBL(String donateYBBL) {
            this.donateYBBL = donateYBBL;
        }

        public String getPay() {
            return pay;
        }

        public void setPay(String pay) {
            this.pay = pay;
        }

        public String getCustomizedonatename() {
            return customizedonatename;
        }

        public void setCustomizedonatename(String customizedonatename) {
            this.customizedonatename = customizedonatename;
        }

        public String getCustomizedonatefee() {
            return customizedonatefee;
        }

        public void setCustomizedonatefee(String customizedonatefee) {
            this.customizedonatefee = customizedonatefee;
        }

        public String getOrderdt() {
            return orderdt;
        }

        public void setOrderdt(String orderdt) {
            this.orderdt = orderdt;
        }

        public String getPaymentid() {
            return paymentid;
        }

        public void setPaymentid(String paymentid) {
            this.paymentid = paymentid;
        }

        public String getStatusid() {
            return statusid;
        }

        public void setStatusid(String statusid) {
            this.statusid = statusid;
        }

        public String getUpdatedt() {
            return updatedt;
        }

        public void setUpdatedt(String updatedt) {
            this.updatedt = updatedt;
        }

        public ArrayList<ProductBean> getOrderdetail() {
            return orderdetail;
        }

        public void setOrderdetail(ArrayList<ProductBean> orderdetail) {
            this.orderdetail = orderdetail;
        }
    }
}
