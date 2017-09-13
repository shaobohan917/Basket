package com.first.basket.bean;

import java.util.List;

/**
 * Created by hanshaobo on 12/09/2017.
 */

public class RecommendBean {
    /**
     * status : 0
     * info : 成功
     * result : {"data":[{"productname":"土豆","productid":"10000049"},{"productname":"西红柿","productid":"10000033"},{"productname":"黄瓜","productid":"10000029"},{"productname":"荷兰豆","productid":"10000040"},{"productname":"牛腩","productid":"10000101"},{"productname":"空心菜","productid":"10000007"},{"productname":"杭白菜","productid":"10000014"},{"productname":"西兰花","productid":"10000023"}]}
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
             * productname : 土豆
             * productid : 10000049
             */

            private String productname;
            private String productid;

            public String getProductname() {
                return productname;
            }

            public void setProductname(String productname) {
                this.productname = productname;
            }

            public String getProductid() {
                return productid;
            }

            public void setProductid(String productid) {
                this.productid = productid;
            }
        }
    }
}
