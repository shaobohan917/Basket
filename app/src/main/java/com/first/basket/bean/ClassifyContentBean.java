package com.first.basket.bean;

import java.util.List;

/**
 * Created by hanshaobo on 10/09/2017.
 */

public class ClassifyContentBean {
        /**
         * status : 0
         * info : 成功
         * result : {"data":[{"id":"10000159","genda":"159","calssificationid":"68","productname":"海天金标生抽","packaging":"","weight":"500毫升","unit":"瓶","cost":"5.08","price":"7.8","Status":"on","img":"10000001.png"},{"id":"10000160","genda":"160","calssificationid":"68","productname":"海天草菇老抽","packaging":"","weight":"500毫升","unit":"瓶","cost":"5.5","price":"8.9","Status":"on","img":"10000001.png"},{"id":"10000161","genda":"161","calssificationid":"68","productname":"海天白米醋","packaging":"","weight":"450毫升","unit":"瓶","cost":"2.83","price":"6.5","Status":"on","img":"10000001.png"},{"id":"10000162","genda":"162","calssificationid":"68","productname":"海天香醋","packaging":"","weight":"445毫升","unit":"瓶","cost":"3.33","price":"6.5","Status":"on","img":"10000001.png"},{"id":"10000163","genda":"163","calssificationid":"68","productname":"金龙鱼芝麻油","packaging":"","weight":"220毫升","unit":"瓶","cost":"13.8","price":"15.8","Status":"on","img":"10000001.png"},{"id":"10000164","genda":"164","calssificationid":"68","productname":"海天上等蚝油","packaging":"","weight":"700克","unit":"瓶","cost":"4.67","price":"7.8","Status":"on","img":"10000001.png"},{"id":"10000165","genda":"165","calssificationid":"68","productname":"欣和六月鲜特级酱油","packaging":"","weight":"500毫升","unit":"瓶","cost":"10","price":"12.8","Status":"on","img":"10000001.png"},{"id":"10000166","genda":"166","calssificationid":"68","productname":"欣和六月鲜上海红烧酱油","packaging":"","weight":"1升","unit":"瓶","cost":"17.08","price":"22.8","Status":"on","img":"10000001.png"},{"id":"10000167","genda":"167","calssificationid":"68","productname":"欣和6月香豆瓣酱","packaging":"","weight":"300克","unit":"盒","cost":"5","price":"7.3","Status":"on","img":"10000001.png"},{"id":"10000168","genda":"168","calssificationid":"68","productname":"家乐鸡精 ","packaging":"","weight":"450克","unit":"袋","cost":"9","price":"10.8","Status":"on","img":"10000001.png"},{"id":"10000169","genda":"169","calssificationid":"68","productname":"佛手99%细味晶 ","packaging":"","weight":"500克","unit":"袋","cost":"8.37","price":"9.5","Status":"on","img":"10000001.png"},{"id":"10000170","genda":"170","calssificationid":"68","productname":"中盐加碘精制盐 ","packaging":"","weight":"500克","unit":"袋","cost":"1.25","price":"1.9","Status":"on","img":"10000001.png"},{"id":"10000171","genda":"171","calssificationid":"68","productname":"中盐无碘精制盐 ","packaging":"","weight":"500克","unit":"袋","cost":"3.3","price":"3.8","Status":"on","img":"10000001.png"},{"id":"10000172","genda":"172","calssificationid":"68","productname":"玉棠白砂糖 ","packaging":"","weight":"500克","unit":"袋","cost":"4.8","price":"5.5","Status":"on","img":"10000001.png"},{"id":"10000173","genda":"173","calssificationid":"68","productname":"宝鼎小麦淀粉","packaging":"","weight":"454克","unit":"袋","cost":"2.6","price":"3.5","Status":"on","img":"10000001.png"}]}
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
                 * id : 10000159
                 * genda : 159
                 * calssificationid : 68
                 * productname : 海天金标生抽
                 * packaging :
                 * weight : 500毫升
                 * unit : 瓶
                 * cost : 5.08
                 * price : 7.8
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

