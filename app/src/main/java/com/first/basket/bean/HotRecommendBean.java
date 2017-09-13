package com.first.basket.bean;

import java.util.List;

/**
 * Created by hanshaobo on 13/09/2017.
 */

public class HotRecommendBean {

    /**
     * data : {"hotimage":"10000001.png","products":[{"id":"10000049","genda":"49","calssificationid":"61","productname":"土豆","packaging":"托盘","weight":"300g","unit":"盒","cost":"1.49","price":"1.8","Status":"on","img":"10000001.png"},{"id":"10000033","genda":"33","calssificationid":"61","productname":"西红柿","packaging":"托盘","weight":"400g","unit":"盒","cost":"3.37","price":"4","Status":"on","img":"10000001.png"},{"id":"10000029","genda":"29","calssificationid":"61","productname":"黄瓜","packaging":"保鲜膜","weight":"400g","unit":"两根","cost":"2.69","price":"3.2","Status":"on","img":"10000001.png"},{"id":"10000040","genda":"40","calssificationid":"61","productname":"荷兰豆","packaging":"托盘","weight":"300g","unit":"盒","cost":"5.54","price":"6.8","Status":"on","img":"10000001.png"},{"id":"10000101","genda":"101","calssificationid":"62","productname":"牛腩","packaging":"","weight":"500g","unit":"盒","cost":"25.35","price":"28.9","Status":"on","img":"10000001.png"},{"id":"10000007","genda":"7","calssificationid":"61","productname":"空心菜","packaging":"包装袋","weight":"300g","unit":"袋","cost":"1.92","price":"2.3","Status":"on","img":"10000001.png"},{"id":"10000014","genda":"14","calssificationid":"61","productname":"杭白菜","packaging":"包装袋","weight":"300g","unit":"袋","cost":"2.21","price":"2.6","Status":"on","img":"10000001.png"},{"id":"10000023","genda":"23","calssificationid":"61","productname":"西兰花","packaging":"保鲜膜","weight":"400g","unit":"棵","cost":"3.37","price":"4","Status":"on","img":"10000001.png"}]}
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
         * hotimage : 10000001.png
         * products : [{"id":"10000049","genda":"49","calssificationid":"61","productname":"土豆","packaging":"托盘","weight":"300g","unit":"盒","cost":"1.49","price":"1.8","Status":"on","img":"10000001.png"},{"id":"10000033","genda":"33","calssificationid":"61","productname":"西红柿","packaging":"托盘","weight":"400g","unit":"盒","cost":"3.37","price":"4","Status":"on","img":"10000001.png"},{"id":"10000029","genda":"29","calssificationid":"61","productname":"黄瓜","packaging":"保鲜膜","weight":"400g","unit":"两根","cost":"2.69","price":"3.2","Status":"on","img":"10000001.png"},{"id":"10000040","genda":"40","calssificationid":"61","productname":"荷兰豆","packaging":"托盘","weight":"300g","unit":"盒","cost":"5.54","price":"6.8","Status":"on","img":"10000001.png"},{"id":"10000101","genda":"101","calssificationid":"62","productname":"牛腩","packaging":"","weight":"500g","unit":"盒","cost":"25.35","price":"28.9","Status":"on","img":"10000001.png"},{"id":"10000007","genda":"7","calssificationid":"61","productname":"空心菜","packaging":"包装袋","weight":"300g","unit":"袋","cost":"1.92","price":"2.3","Status":"on","img":"10000001.png"},{"id":"10000014","genda":"14","calssificationid":"61","productname":"杭白菜","packaging":"包装袋","weight":"300g","unit":"袋","cost":"2.21","price":"2.6","Status":"on","img":"10000001.png"},{"id":"10000023","genda":"23","calssificationid":"61","productname":"西兰花","packaging":"保鲜膜","weight":"400g","unit":"棵","cost":"3.37","price":"4","Status":"on","img":"10000001.png"}]
         */

        private String hotimage;
        private List<ProductsBean> products;

        public String getHotimage() {
            return hotimage;
        }

        public void setHotimage(String hotimage) {
            this.hotimage = hotimage;
        }

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public static class ProductsBean {
            /**
             * id : 10000049
             * genda : 49
             * calssificationid : 61
             * productname : 土豆
             * packaging : 托盘
             * weight : 300g
             * unit : 盒
             * cost : 1.49
             * price : 1.8
             * Status : on
             * img : 10000001.png
             */

            private String id;
            private String genda;
            private String calssificationid;
            private String productname;
            private String packaging;
            private String weight;
            private String unit;
            private String cost;
            private String price;
            private String Status;
            private String img;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGenda() {
                return genda;
            }

            public void setGenda(String genda) {
                this.genda = genda;
            }

            public String getCalssificationid() {
                return calssificationid;
            }

            public void setCalssificationid(String calssificationid) {
                this.calssificationid = calssificationid;
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

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
