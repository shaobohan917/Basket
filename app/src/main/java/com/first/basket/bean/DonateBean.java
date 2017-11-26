package com.first.basket.bean;


import java.util.List;

/**
 * Created by hanshaobo on 26/11/2017.
 */

public class DonateBean {
    /**
     * status : 0
     * info : 成功
     * result : {"data":[{"donatename":"有人公益基金","donatecode":"YRJJ","donateimage":"/AppImage/YRJJ_20171120.png"}]}
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
             * donatename : 有人公益基金
             * donatecode : YRJJ
             * donateimage : /AppImage/YRJJ_20171120.png
             */

            private String donatename;
            private String donatecode;
            private String donateimage;

            public String getDonatename() {
                return donatename;
            }

            public void setDonatename(String donatename) {
                this.donatename = donatename;
            }

            public String getDonatecode() {
                return donatecode;
            }

            public void setDonatecode(String donatecode) {
                this.donatecode = donatecode;
            }

            public String getDonateimage() {
                return donateimage;
            }

            public void setDonateimage(String donateimage) {
                this.donateimage = donateimage;
            }
        }
    }
}

