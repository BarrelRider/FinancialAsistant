package com.example.models;

public class CommodityItem {

    private int resCompIcon;
    private String commodityName;
    private String commodityDate;
    private String buy;
    private String sell;
    private String XofPercent;
    private String lowCommodity;
    private String highCommodity;


    public CommodityItem() {
        this.resCompIcon = 0;
        this.commodityName = null;
        this.commodityDate = null;
        this.buy = null;
        this.sell = null;
        this.XofPercent = null;
        this.lowCommodity = null;
        this.highCommodity = null;
    }
    public CommodityItem(int resCompIcon, String commodityName) {
        this.resCompIcon = resCompIcon;
        this.commodityName = commodityName;
        this.commodityDate = null;
        this.buy = null;
        this.sell = null;

    }
    public CommodityItem(int resCompIcon, String companyName, String stockDate, String buy, String sell) {
        this.resCompIcon = resCompIcon;
        this.commodityName = companyName;
        this.commodityDate = stockDate;
        this.buy = buy;

        this.sell = sell;
    }




    public int getResCompIcon() {
        return resCompIcon;
    }

    public void setResCompIcon(int resCompIcon) {
        this.resCompIcon = resCompIcon;
    }


    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityDate() {
        return commodityDate;
    }

    public void setCommodityDate(String commodityDate) {
        this.commodityDate = commodityDate;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public String getLowCommodity() {
        return lowCommodity;
    }

    public void setLowCommodity(String lowCommodity) {
        this.lowCommodity = lowCommodity;
    }

    public String getHighCommodity() {
        return highCommodity;
    }

    public void setHighStock(String highCommodity) {
        this.lowCommodity = highCommodity;
    }

    public void setHighCommodity(String highCommodity) {
        this.highCommodity = highCommodity;
    }

    public String getXofPercent() {
        return XofPercent;

    }

    public void setXofPercent(String xofPercent) {
        XofPercent = xofPercent;
    }
}

