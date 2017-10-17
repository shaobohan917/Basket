package com.first.basket.bean;

import java.util.List;

/**
 * Created by hanshaobo on 10/10/2017.
 */

public class HomeBean {
    /**
     * data : {"Carouselfigure":[{"image":"/AppImage/Banner_0_20171007.png","url":""},{"image":"/AppImage/Banner_1_20171007.png","url":""},{"image":"/AppImage/Banner_2_20171007.png","url":""},{"image":"/AppImage/Banner_3_20171007.png","url":""},{"image":"/AppImage/Banner_4_20171007.png","url":""}],"SQCS":{"Carouselfigure":[{"image":"/AppImage/SQCS_0_20171007.png","url":""}],"Vegetables":"/AppImage/TJvegetables_20171007.png","Meat":"/AppImage/TJmeat_20171007.png"},"SHCS":{"Image":"/AppImage/SHCS.png","Title":"【安全食品、吃的放心】","URL":""},"HLTG":{"Image":"/AppImage/HLTG.png","Title":"迎中秋，庆国庆，口福享不停","URL":""},"QGCS":{"Image":"/AppImage/QGCS.png","Title":"迎中秋，庆国庆，口福享不停","URL":""},"JKSS":{"Image":"/AppImage/JKSS.png","Title":"迎中秋，庆国庆，口福享不停","URL":""}}
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
         * Carouselfigure : [{"image":"/AppImage/Banner_0_20171007.png","url":""},{"image":"/AppImage/Banner_1_20171007.png","url":""},{"image":"/AppImage/Banner_2_20171007.png","url":""},{"image":"/AppImage/Banner_3_20171007.png","url":""},{"image":"/AppImage/Banner_4_20171007.png","url":""}]
         * SQCS : {"Carouselfigure":[{"image":"/AppImage/SQCS_0_20171007.png","url":""}],"Vegetables":"/AppImage/TJvegetables_20171007.png","Meat":"/AppImage/TJmeat_20171007.png"}
         * SHCS : {"Image":"/AppImage/SHCS.png","Title":"【安全食品、吃的放心】","URL":""}
         * HLTG : {"Image":"/AppImage/HLTG.png","Title":"迎中秋，庆国庆，口福享不停","URL":""}
         * QGCS : {"Image":"/AppImage/QGCS.png","Title":"迎中秋，庆国庆，口福享不停","URL":""}
         * JKSS : {"Image":"/AppImage/JKSS.png","Title":"迎中秋，庆国庆，口福享不停","URL":""}
         */

        private SQCSBean SQCS;
        private SHCSBean SHCS;
        private HLTGBean HLTG;
        private QGCSBean QGCS;
        private JKSSBean JKSS;
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

        public List<CarouselfigureBeanX> getCarouselfigure() {
            return Carouselfigure;
        }

        public void setCarouselfigure(List<CarouselfigureBeanX> Carouselfigure) {
            this.Carouselfigure = Carouselfigure;
        }

        public static class SQCSBean {
            /**
             * Carouselfigure : [{"image":"/AppImage/SQCS_0_20171007.png","url":""}]
             * Vegetables : /AppImage/TJvegetables_20171007.png
             * Meat : /AppImage/TJmeat_20171007.png
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
                 * image : /AppImage/SQCS_0_20171007.png
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
             * Image : /AppImage/SHCS.png
             * Title : 【安全食品、吃的放心】
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

        public static class QGCSBean {
            /**
             * Image : /AppImage/QGCS.png
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
             * image : /AppImage/Banner_0_20171007.png
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
