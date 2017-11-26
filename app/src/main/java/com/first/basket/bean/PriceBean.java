package com.first.basket.bean;

import java.io.Serializable;

/**
 * Created by hanshaobo on 13/09/2017.
 */

public class PriceBean implements Serializable {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * Allprice : 68.90
         * totalprice : 38.90
         * totalcost : 38.90
         * Donateforaxwx : 0.38
         * Donateforybbl : 0.19
         * DonateProject :
         * DonateFee : 0
         * Fare : 30.00
         */

        private Double Allprice;
        private Double totalprice;
        private Double totalcost;
        private Double Donateforaxwx;
        private Double Donateforybbl;
        private String DonateProject;
        private String DonateFee;
        private Double Fare;

        public Double getAllprice() {
            return Allprice;
        }

        public void setAllprice(Double Allprice) {
            this.Allprice = Allprice;
        }

        public Double getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(Double totalprice) {
            this.totalprice = totalprice;
        }

        public Double getTotalcost() {
            return totalcost;
        }

        public void setTotalcost(Double totalcost) {
            this.totalcost = totalcost;
        }

        public Double getDonateforaxwx() {
            return Donateforaxwx;
        }

        public void setDonateforaxwx(Double Donateforaxwx) {
            this.Donateforaxwx = Donateforaxwx;
        }

        public Double getDonateforybbl() {
            return Donateforybbl;
        }

        public void setDonateforybbl(Double Donateforybbl) {
            this.Donateforybbl = Donateforybbl;
        }

        public String getDonateProject() {
            return DonateProject;
        }

        public void setDonateProject(String DonateProject) {
            this.DonateProject = DonateProject;
        }

        public String getDonateFee() {
            return DonateFee;
        }

        public void setDonateFee(String DonateFee) {
            this.DonateFee = DonateFee;
        }

        public Double getFare() {
            return Fare;
        }

        public void setFare(Double Fare) {
            this.Fare = Fare;
        }
    }
}