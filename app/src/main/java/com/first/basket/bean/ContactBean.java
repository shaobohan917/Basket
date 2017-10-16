package com.first.basket.bean;

import java.io.Serializable;

/**
 * Created by hanshaobo on 11/10/2017.
 */

public class ContactBean implements Serializable{

    private String userid;
    private String username;
    private String phone;
    private String address;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
