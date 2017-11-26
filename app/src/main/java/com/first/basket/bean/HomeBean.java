package com.first.basket.bean;

import java.util.List;

/**
 * Created by hanshaobo on 10/10/2017.
 */

public class HomeBean {
    /**
     * data : {"Carouselfigure":[{"image":"/AppImage/Banner_0_20171124.png","url":""},{"image":"/AppImage/Banner_1_20171124.png","url":""},{"image":"/AppImage/Banner_2_20171124.png","url":""},{"image":"/AppImage/Banner_3_20171124.png","url":""},{"image":"/AppImage/Banner_4_20171124.png","url":""}],"SQCS":{"Carouselfigure":[{"image":"/AppImage/SQCS_0_20171125.png","url":""},{"image":"/AppImage/SQCS_1_20171125.png","url":""},{"image":"/AppImage/SQCS_2_20171125.png","url":""},{"image":"/AppImage/SQCS_3_20171125.png","url":""},{"image":"/AppImage/SQCS_4_20171125.png","url":""},{"image":"/AppImage/SQCS_5_20171125.png","url":""},{"image":"/AppImage/SQCS_6_20171125.png","url":""}],"Vegetables":"/AppImage/TJvegetables_0_20171125.png","Meat":"/AppImage/TJmeat_0_20171125.png"},"SHCS":{"Image":"/AppImage/SHCS_20171125.png","Title":"","URL":""},"HLTG":{"Image":"/AppImage/HLTG_20171125.png","Title":"","URL":"http://h5.yhclzgc.com/activity/app/HLTG.html"},"QGCS":{"Image":"/AppImage/QGCS_20171125.png","Title":"","URL":""},"JKSS":{"Image":"/AppImage/JKTS_20171125.png","Title":"","URL":"https://www.yhclzgc.com/yhclz/H5/jkss.html"},"AXWX":{"Image":"/AppImage/AXWX_20171125.png","Title":"","URL":"http://h5.yhclzgc.com/activity/app/love.html"},"YBBL":{"Image":"/AppImage/YBBL_20171125.png","Title":"","URL":"http://h5.yhclzgc.com/activity/app/Medicare.html"}}
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
         * Carouselfigure : [{"image":"/AppImage/Banner_0_20171124.png","url":""},{"image":"/AppImage/Banner_1_20171124.png","url":""},{"image":"/AppImage/Banner_2_20171124.png","url":""},{"image":"/AppImage/Banner_3_20171124.png","url":""},{"image":"/AppImage/Banner_4_20171124.png","url":""}]
         * SQCS : {"Carouselfigure":[{"image":"/AppImage/SQCS_0_20171125.png","url":""},{"image":"/AppImage/SQCS_1_20171125.png","url":""},{"image":"/AppImage/SQCS_2_20171125.png","url":""},{"image":"/AppImage/SQCS_3_20171125.png","url":""},{"image":"/AppImage/SQCS_4_20171125.png","url":""},{"image":"/AppImage/SQCS_5_20171125.png","url":""},{"image":"/AppImage/SQCS_6_20171125.png","url":""}],"Vegetables":"/AppImage/TJvegetables_0_20171125.png","Meat":"/AppImage/TJmeat_0_20171125.png"}
         * SHCS : {"Image":"/AppImage/SHCS_20171125.png","Title":"","URL":""}
         * HLTG : {"Image":"/AppImage/HLTG_20171125.png","Title":"","URL":"http://h5.yhclzgc.com/activity/app/HLTG.html"}
         * QGCS : {"Image":"/AppImage/QGCS_20171125.png","Title":"","URL":""}
         * JKSS : {"Image":"/AppImage/JKTS_20171125.png","Title":"","URL":"https://www.yhclzgc.com/yhclz/H5/jkss.html"}
         * AXWX : {"Image":"/AppImage/AXWX_20171125.png","Title":"","URL":"http://h5.yhclzgc.com/activity/app/love.html"}
         * YBBL : {"Image":"/AppImage/YBBL_20171125.png","Title":"","URL":"http://h5.yhclzgc.com/activity/app/Medicare.html"}
         */

        private SQCSBean SQCS;
        private SHCSBean SHCS;
        private HLTGBean HLTG;
        private QGCSBean QGCS;
        private JKSSBean JKSS;
        private AXWXBean AXWX;
        private YBBLBean YBBL;
        private List<CarouselfigureBeanX> Carouselfigure;

        public SQCSBean getSQCS() {
            return SQCS;
        }

        public void setSQCS(SQCSBean SQCS) {
            this.SQCS = SQCS;
        }

        public SHCSBean getSHCS() {
            return SHCS;
        }

        public void setSHCS(SHCSBean SHCS) {
            this.SHCS = SHCS;
        }

        public HLTGBean getHLTG() {
            return HLTG;
        }

        public void setHLTG(HLTGBean HLTG) {
            this.HLTG = HLTG;
        }

        public QGCSBean getQGCS() {
            return QGCS;
        }

        public void setQGCS(QGCSBean QGCS) {
            this.QGCS = QGCS;
        }

        public JKSSBean getJKSS() {
            return JKSS;
        }

        public void setJKSS(JKSSBean JKSS) {
            this.JKSS = JKSS;
        }

        public AXWXBean getAXWX() {
            return AXWX;
        }

        public void setAXWX(AXWXBean AXWX) {
            this.AXWX = AXWX;
        }

        public YBBLBean getYBBL() {
            return YBBL;
        }

        public void setYBBL(YBBLBean YBBL) {
            this.YBBL = YBBL;
        }

        public List<CarouselfigureBeanX> getCarouselfigure() {
            return Carouselfigure;
        }

        public void setCarouselfigure(List<CarouselfigureBeanX> Carouselfigure) {
            this.Carouselfigure = Carouselfigure;
        }

        public static class SQCSBean {
            /**
             * Carouselfigure : [{"image":"/AppImage/SQCS_0_20171125.png","url":""},{"image":"/AppImage/SQCS_1_20171125.png","url":""},{"image":"/AppImage/SQCS_2_20171125.png","url":""},{"image":"/AppImage/SQCS_3_20171125.png","url":""},{"image":"/AppImage/SQCS_4_20171125.png","url":""},{"image":"/AppImage/SQCS_5_20171125.png","url":""},{"image":"/AppImage/SQCS_6_20171125.png","url":""}]
             * Vegetables : /AppImage/TJvegetables_0_20171125.png
             * Meat : /AppImage/TJmeat_0_20171125.png
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
                 * image : /AppImage/SQCS_0_20171125.png
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

        public static class SHCSBean {
            /**
             * Image : /AppImage/SHCS_20171125.png
             * Title :
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

        public static class HLTGBean {
            /**
             * Image : /AppImage/HLTG_20171125.png
             * Title :
             * URL : http://h5.yhclzgc.com/activity/app/HLTG.html
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

        public static class QGCSBean {
            /**
             * Image : /AppImage/QGCS_20171125.png
             * Title :
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
             * Image : /AppImage/JKTS_20171125.png
             * Title :
             * URL : https://www.yhclzgc.com/yhclz/H5/jkss.html
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

        public static class AXWXBean {
            /**
             * Image : /AppImage/AXWX_20171125.png
             * Title :
             * URL : http://h5.yhclzgc.com/activity/app/love.html
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

        public static class YBBLBean {
            /**
             * Image : /AppImage/YBBL_20171125.png
             * Title :
             * URL : http://h5.yhclzgc.com/activity/app/Medicare.html
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
             * image : /AppImage/Banner_0_20171124.png
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