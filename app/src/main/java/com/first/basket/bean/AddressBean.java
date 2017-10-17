package com.first.basket.bean;

import java.io.Serializable;

/**
 * Created by hanshaobo on 17/10/2017.
 */

public class AddressBean implements Serializable{
    /**
     * addressid : 7002643
     * userid : 6002565
     * receiver :
     * recvphone :
     * street : &
     * subdistrict :
     * district :
     * city :
     * villageid : 50000276
     * status : Y
     * defaultaddr : Y
     * issqcs : n
     * isshcs : n
     * isqgcs : y
     */

    private String addressid;
    private String userid;
    private String receiver;
    private String recvphone;
    private String street;
    private String subdistrict;
    private String district;
    private String city;
    private String villageid;
    private String status;
    private String defaultaddr;
    private String issqcs;
    private String isshcs;
    private String isqgcs;

    public String getAddressid() {
        return addressid;
    }

    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getRecvphone() {
        return recvphone;
    }

    public void setRecvphone(String recvphone) {
        this.recvphone = recvphone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSubdistrict() {
        return subdistrict;
    }

    public void setSubdistrict(String subdistrict) {
        this.subdistrict = subdistrict;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVillageid() {
        return villageid;
    }

    public void setVillageid(String villageid) {
        this.villageid = villageid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDefaultaddr() {
        return defaultaddr;
    }

    public void setDefaultaddr(String defaultaddr) {
        this.defaultaddr = defaultaddr;
    }

    public String getIssqcs() {
        return issqcs;
    }

    public void setIssqcs(String issqcs) {
        this.issqcs = issqcs;
    }

    public String getIsshcs() {
        return isshcs;
    }

    public void setIsshcs(String isshcs) {
        this.isshcs = isshcs;
    }

    public String getIsqgcs() {
        return isqgcs;
    }

    public void setIsqgcs(String isqgcs) {
        this.isqgcs = isqgcs;
    }
}

