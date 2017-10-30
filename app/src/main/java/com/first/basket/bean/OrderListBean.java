package com.first.basket.bean;

import java.util.List;

/**
 * Created by hanshaobo on 12/09/2017.
 */

public class OrderListBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * orderid : 8000366
         * userid : 6002561
         * strorderid : 170530001509367510
         * addressid : 7002556
         * delievedt : 2017-10-31
         * delievetime : 上午
         * delieveweekday :
         * qty : 1
         * cost : 59.90
         * price : 59.90
         * orderdt : 2017-10-30 20:45:10
         * paymentid : 3
         * statusid : 4
         * updatedt : 2017-10-30 20:45:10
         * orderdetail : [{"ordersid":"8000366","productid":"10001177","number":"1","cost":"59.90","price":"59.90","productname":"天润 冰激凌化了酸奶","weight":"180g*12","unit":"箱","img":"/prod_pic/107/10001177.png"}]
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
        private String orderdt;
        private String paymentid;
        private String statusid;
        private String updatedt;
        private List<OrderdetailBean> orderdetail;

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

        public List<OrderdetailBean> getOrderdetail() {
            return orderdetail;
        }

        public void setOrderdetail(List<OrderdetailBean> orderdetail) {
            this.orderdetail = orderdetail;
        }

        public static class OrderdetailBean {
            /**
             * ordersid : 8000366
             * productid : 10001177
             * number : 1
             * cost : 59.90
             * price : 59.90
             * productname : 天润 冰激凌化了酸奶
             * weight : 180g*12
             * unit : 箱
             * img : /prod_pic/107/10001177.png
             */

            private String ordersid;
            private String productid;
            private String number;
            private String cost;
            private String price;
            private String productname;
            private String weight;
            private String unit;
            private String img;

            public String getOrdersid() {
                return ordersid;
            }

            public void setOrdersid(String ordersid) {
                this.ordersid = ordersid;
            }

            public String getProductid() {
                return productid;
            }

            public void setProductid(String productid) {
                this.productid = productid;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
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

            public String getProductname() {
                return productname;
            }

            public void setProductname(String productname) {
                this.productname = productname;
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

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
