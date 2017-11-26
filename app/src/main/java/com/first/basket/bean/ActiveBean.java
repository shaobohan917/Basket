package com.first.basket.bean;

/**
 * Created by hanshaobo on 26/11/2017.
 */

public class ActiveBean {
    /**
     * data : {"activityurl":"http://h5.yhclzgc.com/activity/app/activity.html"}
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
         * activityurl : http://h5.yhclzgc.com/activity/app/activity.html
         */

        private String activityurl;

        public String getActivityurl() {
            return activityurl;
        }

        public void setActivityurl(String activityurl) {
            this.activityurl = activityurl;
        }
    }
}
