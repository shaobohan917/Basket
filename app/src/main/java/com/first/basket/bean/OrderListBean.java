package com.first.basket.bean;

import java.util.List;

/**
 * Created by hanshaobo on 12/09/2017.
 */

public class OrderListBean {
    /**
     * status : 0
     * info : 成功
     * result : {"data":[{"id":"8000013","userid":"6002491","strorderid":"170824131503578175","addressid":"7002489","delievedt":"2017-08-25","delievetime":"上午","delieveweekday":"星期五","qty":"1","cost":"9.35","price":"9.50","orderdt":"2017-08-24 20:36:15","paymentid":"1","statusid":"1","updateby":"1005","updatedt":"2017-08-24 20:36:15"},{"id":"8000014","userid":"6002491","strorderid":"170824141503578313","addressid":"7002489","delievedt":"2017-08-26","delievetime":"上午","delieveweekday":"星期六","qty":"1","cost":"2.69","price":"2.50","orderdt":"2017-08-24 20:38:33","paymentid":"1","statusid":"2","updateby":"1005","updatedt":"2017-08-24 20:52:14"},{"id":"8000015","userid":"6002491","strorderid":"170824151503579018","addressid":"7002489","delievedt":"2017-08-25","delievetime":"上午","delieveweekday":"星期五","qty":"7","cost":"38.19","price":"46.10","orderdt":"2017-08-24 20:50:18","paymentid":"1","statusid":"1","updateby":"1005","updatedt":"2017-08-24 20:50:18"}]}
     */

    private int status;
    private String info;
    private ResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 8000013
             * userid : 6002491
             * strorderid : 170824131503578175
             * addressid : 7002489
             * delievedt : 2017-08-25
             * delievetime : 上午
             * delieveweekday : 星期五
             * qty : 1
             * cost : 9.35
             * price : 9.50
             * orderdt : 2017-08-24 20:36:15
             * paymentid : 1
             * statusid : 1
             * updateby : 1005
             * updatedt : 2017-08-24 20:36:15
             */

            private String id;
            private String userid;
            private String strorderid;
            private String addressid;
            private String delievedt;
            private String delievetime;
            private String delieveweekday;
            private String qty;
            private String cost;
            private String price;
            private String orderdt;
            private String paymentid;
            private String statusid;
            private String updateby;
            private String updatedt;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getUpdateby() {
                return updateby;
            }

            public void setUpdateby(String updateby) {
                this.updateby = updateby;
            }

            public String getUpdatedt() {
                return updatedt;
            }

            public void setUpdatedt(String updatedt) {
                this.updatedt = updatedt;
            }
        }
    }
}
