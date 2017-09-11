package com.first.basket.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hanshaobo on 10/09/2017.
 */

public class GoodsDetailBean implements Serializable {
    /**
     * status : 0
     * info : 成功
     * result : {"data":{"Images":[{"image":"10000001.png","url":""},{"image":"10000001.png","url":""},{"image":"10000001.png","url":""},{"image":"10000001.png","url":""}],"title":null,"Subtitle":"【今天下单，明日采摘，后天到家】","price":null,"productdetail":"空心菜是碱性食物，并含有钾、氯等调节水液平衡的元素，食后可降低肠道的酸度，预防肠道内的菌群失调，对防癌有益。所含的烟酸、维生素C等能降低胆固醇、甘油三酯，具有降脂减肥的功效。空心菜中的叶绿素有\u201c绿色精灵\u201d之称，可洁齿防龋除口臭，健美皮肤。"}}
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

    public static class ResultBean implements Serializable {
        /**
         * data : {"Images":[{"image":"10000001.png","url":""},{"image":"10000001.png","url":""},{"image":"10000001.png","url":""},{"image":"10000001.png","url":""}],"title":null,"Subtitle":"【今天下单，明日采摘，后天到家】","price":null,"productdetail":"空心菜是碱性食物，并含有钾、氯等调节水液平衡的元素，食后可降低肠道的酸度，预防肠道内的菌群失调，对防癌有益。所含的烟酸、维生素C等能降低胆固醇、甘油三酯，具有降脂减肥的功效。空心菜中的叶绿素有\u201c绿色精灵\u201d之称，可洁齿防龋除口臭，健美皮肤。"}
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean implements Serializable {
            /**
             * Images : [{"image":"10000001.png","url":""},{"image":"10000001.png","url":""},{"image":"10000001.png","url":""},{"image":"10000001.png","url":""}]
             * title : null
             * Subtitle : 【今天下单，明日采摘，后天到家】
             * price : null
             * productdetail : 空心菜是碱性食物，并含有钾、氯等调节水液平衡的元素，食后可降低肠道的酸度，预防肠道内的菌群失调，对防癌有益。所含的烟酸、维生素C等能降低胆固醇、甘油三酯，具有降脂减肥的功效。空心菜中的叶绿素有“绿色精灵”之称，可洁齿防龋除口臭，健美皮肤。
             */

            private Object title;
            private String Subtitle;
            private Object price;
            private String productdetail;
            private List<ImagesBean> Images;

            public Object getTitle() {
                return title;
            }

            public void setTitle(Object title) {
                this.title = title;
            }

            public String getSubtitle() {
                return Subtitle;
            }

            public void setSubtitle(String Subtitle) {
                this.Subtitle = Subtitle;
            }

            public Object getPrice() {
                return price;
            }

            public void setPrice(Object price) {
                this.price = price;
            }

            public String getProductdetail() {
                return productdetail;
            }

            public void setProductdetail(String productdetail) {
                this.productdetail = productdetail;
            }

            public List<ImagesBean> getImages() {
                return Images;
            }

            public void setImages(List<ImagesBean> Images) {
                this.Images = Images;
            }

            public static class ImagesBean {
                /**
                 * image : 10000001.png
                 * url :
                 */

                private String image;
                private String url;

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}

