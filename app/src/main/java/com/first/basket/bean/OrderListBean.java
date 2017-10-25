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
         * orderid : 8000260
         * userid : 6002565
         * strorderid : 170404571508929094
         * addressid : 7002705
         * delievedt : 2017-10-26
         * delievetime : 上午
         * delieveweekday :
         * qty : 20
         * cost : 362.90
         * price : 466.90
         * orderdt : 2017-10-25 18:58:12
         * paymentid : 1
         * statusid : 1
         * updatedt : 2017-10-25 18:58:12
         * orderdetail : [{"ordersid":"8000260","productid":"10001647","number":"1","cost":"9.90","price":"9.90","productname":"冬枣","weight":"1斤","unit":"份","img":"/prod_pic/102/10001647.png"},{"ordersid":"8000260","productid":"10001646","number":"1","cost":"9.00","price":"9.00","productname":"砀山梨","weight":"3斤","unit":"份","img":"/prod_pic/102/10001646.png"},{"ordersid":"8000260","productid":"10001645","number":"2","cost":"39.80","price":"39.80","productname":"红蛇果","weight":"4粒","unit":"份","img":"/prod_pic/102/10001645.png"},{"ordersid":"8000260","productid":"10000591","number":"1","cost":"40.00","price":"45.00","productname":"崇明麻鸭","weight":"2斤以上","unit":"份","img":"/prod_pic/103/10000591.png"},{"ordersid":"8000260","productid":"10001643","number":"3","cost":"38.40","price":"38.40","productname":"蜜柚","weight":"3斤以上","unit":"个","img":"/prod_pic/102/10001643.png"},{"ordersid":"8000260","productid":"10001642","number":"1","cost":"10.00","price":"10.00","productname":"国产香蕉","weight":"3斤","unit":"份","img":"/prod_pic/102/10001642.png"},{"ordersid":"8000260","productid":"10000581","number":"1","cost":"9.50","price":"9.50","productname":"库尔勒香梨","weight":"500g","unit":"包","img":"/prod_pic/102/10000581.png"},{"ordersid":"8000260","productid":"10000799","number":"1","cost":"25.00","price":"40.00","productname":"万有全 腊鸡腿","weight":"500g","unit":"袋","img":"/prod_pic/103/10000799.png"},{"ordersid":"8000260","productid":"10001620","number":"1","cost":"21.00","price":"30.00","productname":"万有全 广式香肠","weight":"300g","unit":"袋","img":"/prod_pic/103/10001620.png"},{"ordersid":"8000260","productid":"10001621","number":"1","cost":"18.00","price":"26.00","productname":"万有全 青鱼干","weight":"250g","unit":"袋","img":"/prod_pic/103/10001621.png"},{"ordersid":"8000260","productid":"10001622","number":"1","cost":"48.30","price":"69.00","productname":"万有全 咸肉五花肉","weight":"500g","unit":"袋","img":"/prod_pic/103/10001622.png"},{"ordersid":"8000260","productid":"10001623","number":"1","cost":"18.00","price":"26.00","productname":"万有全 肉枣","weight":"250g","unit":"袋","img":"/prod_pic/103/10001623.png"},{"ordersid":"8000260","productid":"10001025","number":"1","cost":"15.00","price":"25.00","productname":"中粮艾谷 有机大豆","weight":"300g","unit":"袋","img":"/prod_pic/105/10001025.png"},{"ordersid":"8000260","productid":"10001024","number":"1","cost":"15.00","price":"19.00","productname":"中粮艾谷 有机糙米","weight":"400g","unit":"袋","img":"/prod_pic/105/10001024.png"},{"ordersid":"8000260","productid":"10001023","number":"1","cost":"15.00","price":"19.50","productname":"中粮艾谷 有机白芸豆","weight":"330g","unit":"袋","img":"/prod_pic/105/10001023.png"},{"ordersid":"8000260","productid":"10001022","number":"1","cost":"16.00","price":"25.80","productname":"中粮艾谷 有机白糯米","weight":"380g","unit":"袋","img":"/prod_pic/105/10001022.png"},{"ordersid":"8000260","productid":"10001021","number":"1","cost":"15.00","price":"25.00","productname":"中粮艾谷 有机八宝米","weight":"360g","unit":"袋","img":"/prod_pic/105/10001021.png"}]
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
             * ordersid : 8000260
             * productid : 10001647
             * number : 1
             * cost : 9.90
             * price : 9.90
             * productname : 冬枣
             * weight : 1斤
             * unit : 份
             * img : /prod_pic/102/10001647.png
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