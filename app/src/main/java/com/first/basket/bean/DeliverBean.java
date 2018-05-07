package com.first.basket.bean;

import java.util.List;

/**
 * Created by hanshaobo on 2018/5/3.
 */

public class DeliverBean {
    /**
     * data : {"sqcs":[{"city":"上海","district":"浦东新区","subdistrict":"潍坊新村街道"},{"city":"上海","district":"浦东新区","subdistrict":"陆家嘴街道"},{"city":"上海","district":"浦东新区","subdistrict":"塘桥街道"},{"city":"上海","district":"浦东新区","subdistrict":"洋泾街道"},{"city":"上海","district":"浦东新区","subdistrict":"东明路街道"}],"yhcs":[{"city":"上海","district":"浦东新区","subdistrict":"潍坊新村街道"},{"city":"上海","district":"浦东新区","subdistrict":"陆家嘴街道"},{"city":"上海","district":"浦东新区","subdistrict":"周家渡街道"},{"city":"上海","district":"浦东新区","subdistrict":"塘桥街道"},{"city":"上海","district":"浦东新区","subdistrict":"上钢新村街道"},{"city":"上海","district":"浦东新区","subdistrict":"南码头路街道"},{"city":"上海","district":"浦东新区","subdistrict":"沪东新村街道"},{"city":"上海","district":"浦东新区","subdistrict":"金杨新村街道"},{"city":"上海","district":"浦东新区","subdistrict":"洋泾街道"},{"city":"上海","district":"浦东新区","subdistrict":"浦兴路街道"},{"city":"上海","district":"浦东新区","subdistrict":"东明路街道"},{"city":"上海","district":"浦东新区","subdistrict":"花木街道"},{"city":"上海","district":"浦东新区","subdistrict":"唐镇"},{"city":"上海","district":"浦东新区","subdistrict":"金桥镇"},{"city":"上海","district":"浦东新区","subdistrict":"高行镇"},{"city":"上海","district":"浦东新区","subdistrict":"张江镇"},{"city":"上海","district":"浦东新区","subdistrict":"三林镇"},{"city":"上海","district":"浦东新区","subdistrict":"康桥镇"},{"city":"上海","district":"浦东新区","subdistrict":"航头镇"},{"city":"上海","district":"黄浦区","subdistrict":"南京东路街道"},{"city":"上海","district":"黄浦区","subdistrict":"外滩街道"},{"city":"上海","district":"黄浦区","subdistrict":"半淞园路街道"},{"city":"上海","district":"黄浦区","subdistrict":"小东门街道"},{"city":"上海","district":"黄浦区","subdistrict":"御园街道"},{"city":"上海","district":"黄浦区","subdistrict":"老西门街道"},{"city":"上海","district":"黄浦区","subdistrict":"五里桥街道"},{"city":"上海","district":"黄浦区","subdistrict":"打浦桥街道"},{"city":"上海","district":"黄浦区","subdistrict":"淮海中路街道"},{"city":"上海","district":"黄浦区","subdistrict":"瑞金二路街道"},{"city":"上海","district":"静安区","subdistrict":"江宁路街道"},{"city":"上海","district":"静安区","subdistrict":"石门二路街道"},{"city":"上海","district":"静安区","subdistrict":"南京西路街道"},{"city":"上海","district":"静安区","subdistrict":"静安寺街道"},{"city":"上海","district":"静安区","subdistrict":"曹家渡街道"},{"city":"上海","district":"静安区","subdistrict":"天目西路街道"},{"city":"上海","district":"静安区","subdistrict":"北站街道"},{"city":"上海","district":"静安区","subdistrict":"宝山路街道"},{"city":"上海","district":"静安区","subdistrict":"共和新路街道"},{"city":"上海","district":"静安区","subdistrict":"大宁路街道"},{"city":"上海","district":"静安区","subdistrict":"彭浦新村街道"},{"city":"上海","district":"静安区","subdistrict":"临汾路街道"},{"city":"上海","district":"静安区","subdistrict":"芷江西路街道"},{"city":"上海","district":"静安区","subdistrict":"彭浦镇"},{"city":"上海","district":"黄浦区","subdistrict":"豫园街道"},{"city":"上海","district":"杨浦区","subdistrict":"定海路街道"},{"city":"上海","district":"杨浦区","subdistrict":"平凉路街道"},{"city":"上海","district":"杨浦区","subdistrict":"江浦路街道"},{"city":"上海","district":"杨浦区","subdistrict":"四平路街道"},{"city":"上海","district":"杨浦区","subdistrict":"控江路街道"},{"city":"上海","district":"杨浦区","subdistrict":"长白新村街道"},{"city":"上海","district":"杨浦区","subdistrict":"延吉新村街道"},{"city":"上海","district":"杨浦区","subdistrict":"殷行街道"},{"city":"上海","district":"杨浦区","subdistrict":"大桥街道"},{"city":"上海","district":"杨浦区","subdistrict":"五角场街道"},{"city":"上海","district":"杨浦区","subdistrict":"新江湾城街道"},{"city":"上海","district":"杨浦区","subdistrict":"五角场镇"},{"city":"上海","district":"虹口区","subdistrict":"欧阳路街道"},{"city":"上海","district":"虹口区","subdistrict":"曲阳路街道"},{"city":"上海","district":"虹口区","subdistrict":"广中路街道"},{"city":"上海","district":"虹口区","subdistrict":"嘉兴路街道"},{"city":"上海","district":"虹口区","subdistrict":"凉城新村街道"},{"city":"上海","district":"虹口区","subdistrict":"四川北路街道"},{"city":"上海","district":"虹口区","subdistrict":"提篮桥街道"},{"city":"上海","district":"虹口区","subdistrict":"江湾镇街道"},{"city":"上海","district":"普陀区","subdistrict":"曹杨新村街道"},{"city":"上海","district":"普陀区","subdistrict":"长风新村街道"},{"city":"上海","district":"普陀区","subdistrict":"长寿路街道"},{"city":"上海","district":"普陀区","subdistrict":"甘泉路街道"},{"city":"上海","district":"普陀区","subdistrict":"石泉路街道"},{"city":"上海","district":"普陀区","subdistrict":"宜川路街道"},{"city":"上海","district":"普陀区","subdistrict":"万里街道"},{"city":"上海","district":"普陀区","subdistrict":"真如镇街道"},{"city":"上海","district":"普陀区","subdistrict":"长征镇"},{"city":"上海","district":"普陀区","subdistrict":"桃浦镇"},{"city":"上海","district":"长宁区","subdistrict":"华阳路街道"},{"city":"上海","district":"长宁区","subdistrict":"新华路街道"},{"city":"上海","district":"长宁区","subdistrict":"江苏路街道"},{"city":"上海","district":"长宁区","subdistrict":"天山路街道"},{"city":"上海","district":"长宁区","subdistrict":"周家桥街道"},{"city":"上海","district":"长宁区","subdistrict":"虹桥街道"},{"city":"上海","district":"长宁区","subdistrict":"仙霞新村街道"},{"city":"上海","district":"长宁区","subdistrict":"程家桥街道"},{"city":"上海","district":"长宁区","subdistrict":"北新泾街道"},{"city":"上海","district":"长宁区","subdistrict":"新泾镇"},{"city":"上海","district":"徐汇区","subdistrict":"湖南路街道"},{"city":"上海","district":"徐汇区","subdistrict":"天平路街道"},{"city":"上海","district":"徐汇区","subdistrict":"枫林路街道"},{"city":"上海","district":"徐汇区","subdistrict":"徐家汇街道"},{"city":"上海","district":"徐汇区","subdistrict":"斜土路街道"},{"city":"上海","district":"徐汇区","subdistrict":"长桥街道"},{"city":"上海","district":"徐汇区","subdistrict":"漕河泾街道"},{"city":"上海","district":"徐汇区","subdistrict":"康健新村街道"},{"city":"上海","district":"徐汇区","subdistrict":"虹梅路街道"},{"city":"上海","district":"徐汇区","subdistrict":"田林街道"},{"city":"上海","district":"徐汇区","subdistrict":"凌云路街道"},{"city":"上海","district":"徐汇区","subdistrict":"龙华街道"},{"city":"上海","district":"闵行区","subdistrict":"古美路街道"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<SqcsBean> sqcs;
        private List<YhcsBean> yhcs;
        private List<YhcsBean> shcs;

        public List<YhcsBean> getShcs() {
            return shcs;
        }

        public void setShcs(List<YhcsBean> shcs) {
            this.shcs = shcs;
        }

        public List<SqcsBean> getSqcs() {
            return sqcs;
        }

        public void setSqcs(List<SqcsBean> sqcs) {
            this.sqcs = sqcs;
        }

        public List<YhcsBean> getYhcs() {
            return yhcs;
        }

        public void setYhcs(List<YhcsBean> yhcs) {
            this.yhcs = yhcs;
        }

        public static class SqcsBean {
            /**
             * city : 上海
             * district : 浦东新区
             * subdistrict : 潍坊新村街道
             */

            private String city;
            private String district;
            private String subdistrict;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getSubdistrict() {
                return subdistrict;
            }

            public void setSubdistrict(String subdistrict) {
                this.subdistrict = subdistrict;
            }
        }

        public static class YhcsBean {
            /**
             * city : 上海
             * district : 浦东新区
             * subdistrict : 潍坊新村街道
             */

            private String city;
            private String district;
            private String subdistrict;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getSubdistrict() {
                return subdistrict;
            }

            public void setSubdistrict(String subdistrict) {
                this.subdistrict = subdistrict;
            }
        }
    }
}
