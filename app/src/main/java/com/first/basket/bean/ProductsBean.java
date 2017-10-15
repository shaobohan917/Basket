package com.first.basket.bean;

/**
 * Created by hanshaobo on 11/10/2017.
 */

public class ProductsBean {
    /**
     * productid : 10000489
     * genda : 50
     * calssificationid : null
     * level2id : 101002
     * productname : 土豆
     * packaging : 马夹袋
     * weight : 2斤
     * unit : 份
     * cost : 5.5
     * price : 5.5
     * Status : on
     * channelid : 1
     * img : /prod_pic/101/10000489.png
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
    private int amount;
    private boolean isCheck;

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
}
