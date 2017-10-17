package com.first.basket.bean;

import java.io.Serializable;

/**
 * Created by hanshaobo on 11/10/2017.
 */

public class ProductBean implements Serializable {
    /**
     * productid : 10000483
     * genda : 30
     * calssificationid : null
     * level2id : 101003
     * productname : 西红柿
     * packaging : 马夹袋
     * weight : 1斤
     * unit : 份
     * cost : 5.3
     * price : 4
     * Status : on
     * channelid : 1
     * img : /prod_pic/101/10000483.png
     * promboolean : y
     * promdata : {"promprice":"3","promstarttime":"2017-10-15 00:00:01","promendtime":"2017-10-28 23:59:59","promlimit":"1","promproducttype":"素"}
     */

    private String productid;
    private String genda;
    private Object calssificationid;
    private String level2id;
    private String productname;
    private String packaging;
    private String weight;
    private String unit;
    private String cost;
    private String price;
    private String Status;
    private String channelid;
    private String img;
    private String promboolean;
    private PromdataBean promdata;

    private int amount;
    private boolean isCheck;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getGenda() {
        return genda;
    }

    public void setGenda(String genda) {
        this.genda = genda;
    }

    public Object getCalssificationid() {
        return calssificationid;
    }

    public void setCalssificationid(Object calssificationid) {
        this.calssificationid = calssificationid;
    }

    public String getLevel2id() {
        return level2id;
    }

    public void setLevel2id(String level2id) {
        this.level2id = level2id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPromboolean() {
        return promboolean;
    }

    public void setPromboolean(String promboolean) {
        this.promboolean = promboolean;
    }

    public PromdataBean getPromdata() {
        return promdata;
    }

    public void setPromdata(PromdataBean promdata) {
        this.promdata = promdata;
    }

    public static class PromdataBean {
        /**
         * promprice : 3
         * promstarttime : 2017-10-15 00:00:01
         * promendtime : 2017-10-28 23:59:59
         * promlimit : 1
         * promproducttype : 素
         */

        private String promprice;
        private String promstarttime;
        private String promendtime;
        private String promlimit;
        private String promproducttype;

        public String getPromprice() {
            return promprice;
        }

        public void setPromprice(String promprice) {
            this.promprice = promprice;
        }

        public String getPromstarttime() {
            return promstarttime;
        }

        public void setPromstarttime(String promstarttime) {
            this.promstarttime = promstarttime;
        }

        public String getPromendtime() {
            return promendtime;
        }

        public void setPromendtime(String promendtime) {
            this.promendtime = promendtime;
        }

        public String getPromlimit() {
            return promlimit;
        }

        public void setPromlimit(String promlimit) {
            this.promlimit = promlimit;
        }

        public String getPromproducttype() {
            return promproducttype;
        }

        public void setPromproducttype(String promproducttype) {
            this.promproducttype = promproducttype;
        }
    }
}
