package com.example.models;

public class MoneyItem {

        private String moneyType;
        private String moneyLow;
        private String moneyHigh;
        private String currentDate;
        private String dailyLow;
        private String dailyHigh;

    public MoneyItem() {
        this.moneyType = null;
        this.currentDate=null;
        this.moneyLow = null;
        this.moneyHigh = null;
        this.dailyLow=null;
        this.dailyHigh=null;
    }
    public MoneyItem(String moneyType, String moneyLow, String moneyHigh) {
        this.moneyType = moneyType;
        this.moneyLow = moneyLow;
        this.moneyHigh = moneyHigh;
        this.currentDate=null;
        this.dailyLow=null;
        this.dailyHigh=null;
    }


    public MoneyItem(String moneyType,String currentDate, String moneyLow, String moneyHigh,String dailyLow,String dailyHigh) {
        this.moneyType = moneyType;
        this.currentDate=currentDate;
        this.moneyLow = moneyLow;
        this.moneyHigh = moneyHigh;
        this.dailyLow=dailyLow;
        this.dailyHigh=dailyHigh;
    }

    public String getMoneyHigh() {
        return moneyHigh;
    }
    public void setMoneyHigh(String moneyHigh) {
        this.moneyHigh = moneyHigh;
    }
    public String getMoneyType() {
        return moneyType;
    }
    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }
    public String getMoneyLow() {
        return moneyLow;
    }
    public void setMoneyLow(String moneyLow) {
        this.moneyLow = moneyLow;
    }
    public String getCurrentDate() {
        return currentDate;
    }
    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
    public String getDailyLow() {
        return dailyLow;
    }
    public void setDailyLow(String dailyLow) {
        this.dailyLow = dailyLow;
    }
    public String getDailyHigh() {
        return dailyHigh;
    }
    public void setDailyHigh(String dailyHigh) {
        this.dailyHigh = dailyHigh;
    }
}
