package com.first.basket.bean;

/**
 * Created by hanshaobo on 15/10/2017.
 */

public class LoginBean {
    /**
     * data : {"userid":"6002559","username":"","phone":"18221971917"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userid : 6002559
         * username :
         * phone : 18221971917
         */

        private String userid;
        private String username;
        private String phone;
        private int isreal;
        private String realname;
        private String YBBLintegral;
        private String AXJJintegral;
        private String userphoto;

        public int getIsreal() {
            return isreal;
        }

        public void setIsreal(int isreal) {
            this.isreal = isreal;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getYBBLintegral() {
            return YBBLintegral;
        }

        public void setYBBLintegral(String YBBLintegral) {
            this.YBBLintegral = YBBLintegral;
        }

        public String getAXJJintegral() {
            return AXJJintegral;
        }

        public void setAXJJintegral(String AXJJintegral) {
            this.AXJJintegral = AXJJintegral;
        }

        public String getUserphoto() {
            return userphoto;
        }

        public void setUserphoto(String userphoto) {
            this.userphoto = userphoto;
        }

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
    }
}
