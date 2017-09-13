package com.first.basket.bean;

import java.util.List;

/**
 * Created by hanshaobo on 12/09/2017.
 */

public class HomeBean {
    /**
     * status : 0
     * info : 成功
     * result : {"data":{"Carouselfigure":[{"image":"/AppImage/TopImage.png","url":""},{"image":"/AppImage/TopImage.png","url":""},{"image":"/AppImage/TopImage.png","url":""},{"image":"/AppImage/TopImage.png","url":""}],"SQCS":{"Carouselfigure":[{"image":"/AppImage/SQCS_1.png","url":""},{"image":"/AppImage/SQCS_1.png","url":""}],"Vegetables":"/AppImage/TJvegetables.png","Meat":"/AppImage/TJmeat.png"},"YHCS":{"Image":"/AppImage/YHCS.png","Title":"【今日下单，明日采摘，后天到家】","Recommend":[{"productid":"10000049","genda":"100267917","calssificationid":"61","level2id":"101002","productname":"土豆","packaging":"托盘","weight":"300g","unit":"盒","cost":"1.49","price":"1.8","Status":"off","img":"/AppImage/10000135_20170908.png"},{"productid":"10000033","genda":"100267905","calssificationid":"61","level2id":"101003","productname":"西红柿","packaging":"托盘","weight":"400g","unit":"盒","cost":"3.37","price":"4","Status":"off","img":"/AppImage/10000135_20170908.png"},{"productid":"10000029","genda":"63","calssificationid":"61","level2id":"101003","productname":"黄瓜","packaging":"保鲜膜","weight":"400g","unit":"两根","cost":"2.69","price":"3.2","Status":"on","img":"/AppImage/10000135_20170908.png"},{"productid":"10000040","genda":"75","calssificationid":"61","level2id":"101003","productname":"荷兰豆","packaging":"托盘","weight":"300g","unit":"盒","cost":"5.54","price":"6.8","Status":"on","img":"/AppImage/10000135_20170908.png"},{"productid":"10000101","genda":"310","calssificationid":"62","level2id":"103002","productname":"牛腩","packaging":"","weight":"500g","unit":"盒","cost":"25.35","price":"28.9","Status":"on","img":"/AppImage/10000135_20170908.png"},{"productid":"10000007","genda":"100267932","calssificationid":"61","level2id":"101001","productname":"空心菜","packaging":"包装袋","weight":"300g","unit":"袋","cost":"1.92","price":"2.3","Status":"off","img":"/AppImage/10000135_20170908.png"}]},"HLTG":{"Image":"/AppImage/HLTG.png","Title":"迎中秋，庆国庆，口福享不停","URL":""},"QYFL":{"Image":"/AppImage/QYFL.png","Title":"迎中秋，庆国庆，口福享不停","URL":""},"JKSS":{"Image":"/AppImage/JKSS.png","Title":"迎中秋，庆国庆，口福享不停","URL":""}}}
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
         * data : {"Carouselfigure":[{"image":"/AppImage/TopImage.png","url":""},{"image":"/AppImage/TopImage.png","url":""},{"image":"/AppImage/TopImage.png","url":""},{"image":"/AppImage/TopImage.png","url":""}],"SQCS":{"Carouselfigure":[{"image":"/AppImage/SQCS_1.png","url":""},{"image":"/AppImage/SQCS_1.png","url":""}],"Vegetables":"/AppImage/TJvegetables.png","Meat":"/AppImage/TJmeat.png"},"YHCS":{"Image":"/AppImage/YHCS.png","Title":"【今日下单，明日采摘，后天到家】","Recommend":[{"productid":"10000049","genda":"100267917","calssificationid":"61","level2id":"101002","productname":"土豆","packaging":"托盘","weight":"300g","unit":"盒","cost":"1.49","price":"1.8","Status":"off","img":"/AppImage/10000135_20170908.png"},{"productid":"10000033","genda":"100267905","calssificationid":"61","level2id":"101003","productname":"西红柿","packaging":"托盘","weight":"400g","unit":"盒","cost":"3.37","price":"4","Status":"off","img":"/AppImage/10000135_20170908.png"},{"productid":"10000029","genda":"63","calssificationid":"61","level2id":"101003","productname":"黄瓜","packaging":"保鲜膜","weight":"400g","unit":"两根","cost":"2.69","price":"3.2","Status":"on","img":"/AppImage/10000135_20170908.png"},{"productid":"10000040","genda":"75","calssificationid":"61","level2id":"101003","productname":"荷兰豆","packaging":"托盘","weight":"300g","unit":"盒","cost":"5.54","price":"6.8","Status":"on","img":"/AppImage/10000135_20170908.png"},{"productid":"10000101","genda":"310","calssificationid":"62","level2id":"103002","productname":"牛腩","packaging":"","weight":"500g","unit":"盒","cost":"25.35","price":"28.9","Status":"on","img":"/AppImage/10000135_20170908.png"},{"productid":"10000007","genda":"100267932","calssificationid":"61","level2id":"101001","productname":"空心菜","packaging":"包装袋","weight":"300g","unit":"袋","cost":"1.92","price":"2.3","Status":"off","img":"/AppImage/10000135_20170908.png"}]},"HLTG":{"Image":"/AppImage/HLTG.png","Title":"迎中秋，庆国庆，口福享不停","URL":""},"QYFL":{"Image":"/AppImage/QYFL.png","Title":"迎中秋，庆国庆，口福享不停","URL":""},"JKSS":{"Image":"/AppImage/JKSS.png","Title":"迎中秋，庆国庆，口福享不停","URL":""}}
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
             * Carouselfigure : [{"image":"/AppImage/TopImage.png","url":""},{"image":"/AppImage/TopImage.png","url":""},{"image":"/AppImage/TopImage.png","url":""},{"image":"/AppImage/TopImage.png","url":""}]
             * SQCS : {"Carouselfigure":[{"image":"/AppImage/SQCS_1.png","url":""},{"image":"/AppImage/SQCS_1.png","url":""}],"Vegetables":"/AppImage/TJvegetables.png","Meat":"/AppImage/TJmeat.png"}
             * YHCS : {"Image":"/AppImage/YHCS.png","Title":"【今日下单，明日采摘，后天到家】","Recommend":[{"productid":"10000049","genda":"100267917","calssificationid":"61","level2id":"101002","productname":"土豆","packaging":"托盘","weight":"300g","unit":"盒","cost":"1.49","price":"1.8","Status":"off","img":"/AppImage/10000135_20170908.png"},{"productid":"10000033","genda":"100267905","calssificationid":"61","level2id":"101003","productname":"西红柿","packaging":"托盘","weight":"400g","unit":"盒","cost":"3.37","price":"4","Status":"off","img":"/AppImage/10000135_20170908.png"},{"productid":"10000029","genda":"63","calssificationid":"61","level2id":"101003","productname":"黄瓜","packaging":"保鲜膜","weight":"400g","unit":"两根","cost":"2.69","price":"3.2","Status":"on","img":"/AppImage/10000135_20170908.png"},{"productid":"10000040","genda":"75","calssificationid":"61","level2id":"101003","productname":"荷兰豆","packaging":"托盘","weight":"300g","unit":"盒","cost":"5.54","price":"6.8","Status":"on","img":"/AppImage/10000135_20170908.png"},{"productid":"10000101","genda":"310","calssificationid":"62","level2id":"103002","productname":"牛腩","packaging":"","weight":"500g","unit":"盒","cost":"25.35","price":"28.9","Status":"on","img":"/AppImage/10000135_20170908.png"},{"productid":"10000007","genda":"100267932","calssificationid":"61","level2id":"101001","productname":"空心菜","packaging":"包装袋","weight":"300g","unit":"袋","cost":"1.92","price":"2.3","Status":"off","img":"/AppImage/10000135_20170908.png"}]}
             * HLTG : {"Image":"/AppImage/HLTG.png","Title":"迎中秋，庆国庆，口福享不停","URL":""}
             * QYFL : {"Image":"/AppImage/QYFL.png","Title":"迎中秋，庆国庆，口福享不停","URL":""}
             * JKSS : {"Image":"/AppImage/JKSS.png","Title":"迎中秋，庆国庆，口福享不停","URL":""}
             */

            private SQCSBean SQCS;
            private YHCSBean YHCS;
            private HLTGBean HLTG;
            private QYFLBean QYFL;
            private JKSSBean JKSS;
            private List<CarouselfigureBeanX> Carouselfigure;

            public SQCSBean getSQCS() {
                return SQCS;
            }

            public void setSQCS(SQCSBean SQCS) {
                this.SQCS = SQCS;
            }

            public YHCSBean getYHCS() {
                return YHCS;
            }

            public void setYHCS(YHCSBean YHCS) {
                this.YHCS = YHCS;
            }

            public HLTGBean getHLTG() {
                return HLTG;
            }

            public void setHLTG(HLTGBean HLTG) {
                this.HLTG = HLTG;
            }

            public QYFLBean getQYFL() {
                return QYFL;
            }

            public void setQYFL(QYFLBean QYFL) {
                this.QYFL = QYFL;
            }

            public JKSSBean getJKSS() {
                return JKSS;
            }

            public void setJKSS(JKSSBean JKSS) {
                this.JKSS = JKSS;
            }

            public List<CarouselfigureBeanX> getCarouselfigure() {
                return Carouselfigure;
            }

            public void setCarouselfigure(List<CarouselfigureBeanX> Carouselfigure) {
                this.Carouselfigure = Carouselfigure;
            }

            public static class SQCSBean {
                /**
                 * Carouselfigure : [{"image":"/AppImage/SQCS_1.png","url":""},{"image":"/AppImage/SQCS_1.png","url":""}]
                 * Vegetables : /AppImage/TJvegetables.png
                 * Meat : /AppImage/TJmeat.png
                 */

                private String Vegetables;
                private String Meat;
                private List<CarouselfigureBean> Carouselfigure;

                public String getVegetables() {
                    return Vegetables;
                }

                public void setVegetables(String Vegetables) {
                    this.Vegetables = Vegetables;
                }

                public String getMeat() {
                    return Meat;
                }

                public void setMeat(String Meat) {
                    this.Meat = Meat;
                }

                public List<CarouselfigureBean> getCarouselfigure() {
                    return Carouselfigure;
                }

                public void setCarouselfigure(List<CarouselfigureBean> Carouselfigure) {
                    this.Carouselfigure = Carouselfigure;
                }

                public static class CarouselfigureBean {
                    /**
                     * image : /AppImage/SQCS_1.png
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

            public static class YHCSBean {
                /**
                 * Image : /AppImage/YHCS.png
                 * Title : 【今日下单，明日采摘，后天到家】
                 * Recommend : [{"productid":"10000049","genda":"100267917","calssificationid":"61","level2id":"101002","productname":"土豆","packaging":"托盘","weight":"300g","unit":"盒","cost":"1.49","price":"1.8","Status":"off","img":"/AppImage/10000135_20170908.png"},{"productid":"10000033","genda":"100267905","calssificationid":"61","level2id":"101003","productname":"西红柿","packaging":"托盘","weight":"400g","unit":"盒","cost":"3.37","price":"4","Status":"off","img":"/AppImage/10000135_20170908.png"},{"productid":"10000029","genda":"63","calssificationid":"61","level2id":"101003","productname":"黄瓜","packaging":"保鲜膜","weight":"400g","unit":"两根","cost":"2.69","price":"3.2","Status":"on","img":"/AppImage/10000135_20170908.png"},{"productid":"10000040","genda":"75","calssificationid":"61","level2id":"101003","productname":"荷兰豆","packaging":"托盘","weight":"300g","unit":"盒","cost":"5.54","price":"6.8","Status":"on","img":"/AppImage/10000135_20170908.png"},{"productid":"10000101","genda":"310","calssificationid":"62","level2id":"103002","productname":"牛腩","packaging":"","weight":"500g","unit":"盒","cost":"25.35","price":"28.9","Status":"on","img":"/AppImage/10000135_20170908.png"},{"productid":"10000007","genda":"100267932","calssificationid":"61","level2id":"101001","productname":"空心菜","packaging":"包装袋","weight":"300g","unit":"袋","cost":"1.92","price":"2.3","Status":"off","img":"/AppImage/10000135_20170908.png"}]
                 */

                private String Image;
                private String Title;
                private List<RecommendBean> Recommend;

                public String getImage() {
                    return Image;
                }

                public void setImage(String Image) {
                    this.Image = Image;
                }

                public String getTitle() {
                    return Title;
                }

                public void setTitle(String Title) {
                    this.Title = Title;
                }

                public List<RecommendBean> getRecommend() {
                    return Recommend;
                }

                public void setRecommend(List<RecommendBean> Recommend) {
                    this.Recommend = Recommend;
                }

                public static class RecommendBean {
                    /**
                     * productid : 10000049
                     * genda : 100267917
                     * calssificationid : 61
                     * level2id : 101002
                     * productname : 土豆
                     * packaging : 托盘
                     * weight : 300g
                     * unit : 盒
                     * cost : 1.49
                     * price : 1.8
                     * Status : off
                     * img : /AppImage/10000135_20170908.png
                     */

                    private String productid;
                    private String genda;
                    private String calssificationid;
                    private String level2id;
                    private String productname;
                    private String packaging;
                    private String weight;
                    private String unit;
                    private String cost;
                    private String price;
                    private String Status;
                    private String img;

                    public String getProductid() {
                        return productid;
                    }

                    public void setProductid(String productid) {
                        this.productid = productid;
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

                    public String getLevel2id() {
                        return level2id;
                    }

                    public void setLevel2id(String level2id) {
                        this.level2id = level2id;
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

            public static class HLTGBean {
                /**
                 * Image : /AppImage/HLTG.png
                 * Title : 迎中秋，庆国庆，口福享不停
                 * URL :
                 */

                private String Image;
                private String Title;
                private String URL;

                public String getImage() {
                    return Image;
                }

                public void setImage(String Image) {
                    this.Image = Image;
                }

                public String getTitle() {
                    return Title;
                }

                public void setTitle(String Title) {
                    this.Title = Title;
                }

                public String getURL() {
                    return URL;
                }

                public void setURL(String URL) {
                    this.URL = URL;
                }
            }

            public static class QYFLBean {
                /**
                 * Image : /AppImage/QYFL.png
                 * Title : 迎中秋，庆国庆，口福享不停
                 * URL :
                 */

                private String Image;
                private String Title;
                private String URL;

                public String getImage() {
                    return Image;
                }

                public void setImage(String Image) {
                    this.Image = Image;
                }

                public String getTitle() {
                    return Title;
                }

                public void setTitle(String Title) {
                    this.Title = Title;
                }

                public String getURL() {
                    return URL;
                }

                public void setURL(String URL) {
                    this.URL = URL;
                }
            }

            public static class JKSSBean {
                /**
                 * Image : /AppImage/JKSS.png
                 * Title : 迎中秋，庆国庆，口福享不停
                 * URL :
                 */

                private String Image;
                private String Title;
                private String URL;

                public String getImage() {
                    return Image;
                }

                public void setImage(String Image) {
                    this.Image = Image;
                }

                public String getTitle() {
                    return Title;
                }

                public void setTitle(String Title) {
                    this.Title = Title;
                }

                public String getURL() {
                    return URL;
                }

                public void setURL(String URL) {
                    this.URL = URL;
                }
            }

            public static class CarouselfigureBeanX {
                /**
                 * image : /AppImage/TopImage.png
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
