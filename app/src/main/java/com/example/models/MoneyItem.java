package com.example.models;

public class MoneyItem {

        private String moneyType;
        private String moneyLow;
        private String moneyHigh;
        private String currentDate;

    public MoneyItem() {
        this.moneyType = null;
        this.currentDate=null;
        this.moneyLow = null;
        this.moneyHigh = null;
    }
    public MoneyItem(String moneyType, String moneyLow, String moneyHigh) {
        this.moneyType = moneyType;
        this.moneyLow = moneyLow;
        this.moneyHigh = moneyHigh;
    }

    public MoneyItem(String moneyType,String currentDate, String moneyLow, String moneyHigh) {
        this.moneyType = moneyType;
        this.currentDate=currentDate;
        this.moneyLow = moneyLow;
        this.moneyHigh = moneyHigh;
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
}
