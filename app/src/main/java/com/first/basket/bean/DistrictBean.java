package com.first.basket.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hanshaobo on 17/10/2017.
 */

public class DistrictBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * subdistrictid : 40000001
         * districtid : 30000011
         * subdistrict : 潍坊新村街道
         * sub_type : 1
         * type_name : 街道
         * svc : y
         */

        private String subdistrictid;
        private String districtid;
        private String subdistrict;
        private String sub_type;
        private String type_name;
        private String svc;

        public String getSubdistrictid() {
            return subdistrictid;
        }

        public void setSubdistrictid(String subdistrictid) {
            this.subdistrictid = subdistrictid;
        }

        public String getDistrictid() {
            return districtid;
        }

        public void setDistrictid(String districtid) {
            this.districtid = districtid;
        }

        public String getSubdistrict() {
            return subdistrict;
        }

        public void setSubdistrict(String subdistrict) {
            this.subdistrict = subdistrict;
        }

        public String getSub_type() {
            return sub_type;
        }

        public void setSub_type(String sub_type) {
            this.sub_type = sub_type;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getSvc() {
            return svc;
        }

        public void setSvc(String svc) {
            this.svc = svc;
        }
    }
}
