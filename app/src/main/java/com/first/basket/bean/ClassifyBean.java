package com.first.basket.bean;


import java.util.List;

/**
 * Created by hanshaobo on 10/09/2017.
 */

public class ClassifyBean {
    /**
     * status : 0
     * info : 成功
     * result : {"data":[{"id":"71","classification":"半成品"},{"id":"72","classification":"饼干糕点"},{"id":"75","classification":"茶冲咖啡"},{"id":"68","classification":"厨房调味"},{"id":"64","classification":"豆制品"},{"id":"73","classification":"方便速食"},{"id":"63","classification":"海鲜水产"},{"id":"74","classification":"酒水饮料"},{"id":"67","classification":"粮油干货"},{"id":"65","classification":"牛奶乳品"},{"id":"62","classification":"肉禽蛋"},{"id":"61","classification":"新鲜蔬菜"},{"id":"66","classification":"新鲜水果"},{"id":"69","classification":"休闲食品"},{"id":"70","classification":"有机蔬菜"}]}
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
             * id : 71
             * classification : 半成品
             */

            private String id;
            private String classification;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getClassification() {
                return classification;
            }

            public void setClassification(String classification) {
                this.classification = classification;
            }
        }
    }
}


