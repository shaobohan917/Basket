package com.first.basket.bean;

/**
 * Created by hanshaobo on 01/11/2017.
 */
public class AliBean {

    /**
     * data : {"out_trade_no":"170763181509539724","preorder_result":"alipay_sdk=alipay-sdk-php-20161101&app_id=2017090408554941&biz_content=%7B%22subject%22%3A+%221%25E5%258F%25B7%25E8%258F%259C%25E7%25AF%25AE%25E5%25AD%2590%25E5%25B7%25A5%25E7%25A8%258B%22%2C%22out_trade_no%22%3A+%22170763181509539724%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%2220%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fapi.yhclzgc.com%2FAPI%2Fnotifyforalipay.php&sign_type=RSA2&timestamp=2017-11-01+20%3A35%3A24&version=1.0&sign=UHc5kUg5XSix0JAM4WRtFl2ko0Nown8pDBS2XaRUwrnA33HzcjPRR6sydHYi64%2Fnqfg8GeOkmgt0IU%2BPGAin7Oi5jjLJ4P9boM8VSAXonyesRQK8EK8tMBUPo2hyukajmge8%2FIaHYi4HWNErXbsPjmGcBB%2FaifUIT29P9Q%2B3RzxVjCvOOQfC3PhJunSvYvHeDQnxBZAWzVBkNOc0nweEpWiIZqHBAE%2FwESRYCihdQQewjze0HhVeo0%2BbmhqsVkLcA70TjL4oEw1bddBmvxC02J%2FurkxNhj8D51XT4obJX6O7Sw2FflZ%2BzQhkntAMGbkPyWHKA4z7ypKqdtt3STMhKg%3D%3D"}
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
         * out_trade_no : 170763181509539724
         * preorder_result : alipay_sdk=alipay-sdk-php-20161101&app_id=2017090408554941&biz_content=%7B%22subject%22%3A+%221%25E5%258F%25B7%25E8%258F%259C%25E7%25AF%25AE%25E5%25AD%2590%25E5%25B7%25A5%25E7%25A8%258B%22%2C%22out_trade_no%22%3A+%22170763181509539724%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%2220%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fapi.yhclzgc.com%2FAPI%2Fnotifyforalipay.php&sign_type=RSA2&timestamp=2017-11-01+20%3A35%3A24&version=1.0&sign=UHc5kUg5XSix0JAM4WRtFl2ko0Nown8pDBS2XaRUwrnA33HzcjPRR6sydHYi64%2Fnqfg8GeOkmgt0IU%2BPGAin7Oi5jjLJ4P9boM8VSAXonyesRQK8EK8tMBUPo2hyukajmge8%2FIaHYi4HWNErXbsPjmGcBB%2FaifUIT29P9Q%2B3RzxVjCvOOQfC3PhJunSvYvHeDQnxBZAWzVBkNOc0nweEpWiIZqHBAE%2FwESRYCihdQQewjze0HhVeo0%2BbmhqsVkLcA70TjL4oEw1bddBmvxC02J%2FurkxNhj8D51XT4obJX6O7Sw2FflZ%2BzQhkntAMGbkPyWHKA4z7ypKqdtt3STMhKg%3D%3D
         */

        private String out_trade_no;
        private String preorder_result;

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getPreorder_result() {
            return preorder_result;
        }

        public void setPreorder_result(String preorder_result) {
            this.preorder_result = preorder_result;
        }
    }
}

