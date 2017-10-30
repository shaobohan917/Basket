package com.first.basket.bean;

import java.util.List;

public class WechatBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * appid : wx7b4dcaf398daa066
         * mchid : 1490509832
         * noncestr : g3L2IAcTXT2H9UxE
         * sign : E07581E2722D177368F2925D9C68C6ED
         * prepayid : wx20171029142232c29c15dd5a0586659549
         */

        private String appid;
        private String mchid;
        private String noncestr;
        private String sign;
        private String prepayid;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getMchid() {
            return mchid;
        }

        public void setMchid(String mchid) {
            this.mchid = mchid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }
    }
}




