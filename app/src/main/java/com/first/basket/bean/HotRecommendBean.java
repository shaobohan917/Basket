package com.first.basket.bean;

import java.util.List;

/**
 * Created by hanshaobo on 13/09/2017.
 */

public class HotRecommendBean {
    /**
     * status : 0
     * info : 成功
     * result : {"data":{"hotimage":"/AppImage/TopImage.png","products":[{"productid":"10000489","genda":"50","calssificationid":null,"level2id":"101002","productname":"土豆","packaging":"马夹袋","weight":"2斤","unit":"份","cost":"5.5","price":"5.5","Status":"on","channelid":"1","img":"/prod_pic/101/10000489.png"},{"productid":"10000483","genda":"30","calssificationid":null,"level2id":"101003","productname":"西红柿","packaging":"马夹袋","weight":"1斤","unit":"份","cost":"5.3","price":"5.3","Status":"on","channelid":"1","img":"/prod_pic/101/10000483.png"},{"productid":"10000537","genda":"32","calssificationid":null,"level2id":"101003","productname":"黄瓜","packaging":"马夹袋","weight":"1斤1两左右","unit":"份","cost":"2.5","price":"2.5","Status":"on","channelid":"1","img":"/prod_pic/101/10000537.png"},{"productid":"10000040","genda":"38","calssificationid":null,"level2id":"101003","productname":"荷兰豆","packaging":"马夹袋","weight":"6两","unit":"份","cost":"10.4","price":"10.4","Status":"on","channelid":"1","img":"/prod_pic/101/10000040.png"},{"productid":"10000101","genda":"82","calssificationid":null,"level2id":"103002","productname":"牛腩","packaging":"","weight":"500g","unit":"盒","cost":"35.8","price":"35.8","Status":"on","channelid":"1","img":"/prod_pic/103/10000101.png"},{"productid":"10000466","genda":"6","calssificationid":null,"level2id":"101001","productname":"空心菜","packaging":"马夹袋","weight":"1斤","unit":"份","cost":"3.8","price":"3.8","Status":"on","channelid":"1","img":"/prod_pic/101/10000466.png"},{"productid":"10000472","genda":"13","calssificationid":null,"level2id":"101001","productname":"杭白菜","packaging":"马夹袋","weight":"1斤","unit":"份","cost":"2.5","price":"2.5","Status":"on","channelid":"1","img":"/prod_pic/101/10000472.png"},{"productid":"10000023","genda":"21","calssificationid":null,"level2id":"101001","productname":"西兰花","packaging":"马夹袋","weight":"约8两","unit":"棵","cost":"4.7","price":"4.7","Status":"on","channelid":"1","img":"/prod_pic/101/10000023.png"}]}}
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
        /**
         * data : {"hotimage":"/AppImage/TopImage.png","products":[{"productid":"10000489","genda":"50","calssificationid":null,"level2id":"101002","productname":"土豆","packaging":"马夹袋","weight":"2斤","unit":"份","cost":"5.5","price":"5.5","Status":"on","channelid":"1","img":"/prod_pic/101/10000489.png"},{"productid":"10000483","genda":"30","calssificationid":null,"level2id":"101003","productname":"西红柿","packaging":"马夹袋","weight":"1斤","unit":"份","cost":"5.3","price":"5.3","Status":"on","channelid":"1","img":"/prod_pic/101/10000483.png"},{"productid":"10000537","genda":"32","calssificationid":null,"level2id":"101003","productname":"黄瓜","packaging":"马夹袋","weight":"1斤1两左右","unit":"份","cost":"2.5","price":"2.5","Status":"on","channelid":"1","img":"/prod_pic/101/10000537.png"},{"productid":"10000040","genda":"38","calssificationid":null,"level2id":"101003","productname":"荷兰豆","packaging":"马夹袋","weight":"6两","unit":"份","cost":"10.4","price":"10.4","Status":"on","channelid":"1","img":"/prod_pic/101/10000040.png"},{"productid":"10000101","genda":"82","calssificationid":null,"level2id":"103002","productname":"牛腩","packaging":"","weight":"500g","unit":"盒","cost":"35.8","price":"35.8","Status":"on","channelid":"1","img":"/prod_pic/103/10000101.png"},{"productid":"10000466","genda":"6","calssificationid":null,"level2id":"101001","productname":"空心菜","packaging":"马夹袋","weight":"1斤","unit":"份","cost":"3.8","price":"3.8","Status":"on","channelid":"1","img":"/prod_pic/101/10000466.png"},{"productid":"10000472","genda":"13","calssificationid":null,"level2id":"101001","productname":"杭白菜","packaging":"马夹袋","weight":"1斤","unit":"份","cost":"2.5","price":"2.5","Status":"on","channelid":"1","img":"/prod_pic/101/10000472.png"},{"productid":"10000023","genda":"21","calssificationid":null,"level2id":"101001","productname":"西兰花","packaging":"马夹袋","weight":"约8两","unit":"棵","cost":"4.7","price":"4.7","Status":"on","channelid":"1","img":"/prod_pic/101/10000023.png"}]}
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
             * hotimage : /AppImage/TopImage.png
             * products : [{"productid":"10000489","genda":"50","calssificationid":null,"level2id":"101002","productname":"土豆","packaging":"马夹袋","weight":"2斤","unit":"份","cost":"5.5","price":"5.5","Status":"on","channelid":"1","img":"/prod_pic/101/10000489.png"},{"productid":"10000483","genda":"30","calssificationid":null,"level2id":"101003","productname":"西红柿","packaging":"马夹袋","weight":"1斤","unit":"份","cost":"5.3","price":"5.3","Status":"on","channelid":"1","img":"/prod_pic/101/10000483.png"},{"productid":"10000537","genda":"32","calssificationid":null,"level2id":"101003","productname":"黄瓜","packaging":"马夹袋","weight":"1斤1两左右","unit":"份","cost":"2.5","price":"2.5","Status":"on","channelid":"1","img":"/prod_pic/101/10000537.png"},{"productid":"10000040","genda":"38","calssificationid":null,"level2id":"101003","productname":"荷兰豆","packaging":"马夹袋","weight":"6两","unit":"份","cost":"10.4","price":"10.4","Status":"on","channelid":"1","img":"/prod_pic/101/10000040.png"},{"productid":"10000101","genda":"82","calssificationid":null,"level2id":"103002","productname":"牛腩","packaging":"","weight":"500g","unit":"盒","cost":"35.8","price":"35.8","Status":"on","channelid":"1","img":"/prod_pic/103/10000101.png"},{"productid":"10000466","genda":"6","calssificationid":null,"level2id":"101001","productname":"空心菜","packaging":"马夹袋","weight":"1斤","unit":"份","cost":"3.8","price":"3.8","Status":"on","channelid":"1","img":"/prod_pic/101/10000466.png"},{"productid":"10000472","genda":"13","calssificationid":null,"level2id":"101001","productname":"杭白菜","packaging":"马夹袋","weight":"1斤","unit":"份","cost":"2.5","price":"2.5","Status":"on","channelid":"1","img":"/prod_pic/101/10000472.png"},{"productid":"10000023","genda":"21","calssificationid":null,"level2id":"101001","productname":"西兰花","packaging":"马夹袋","weight":"约8两","unit":"棵","cost":"4.7","price":"4.7","Status":"on","channelid":"1","img":"/prod_pic/101/10000023.png"}]
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

        }
    }
}
