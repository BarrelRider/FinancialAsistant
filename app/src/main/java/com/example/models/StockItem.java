package com.example.models;

public class StockItem {

    private int resCompIcon;
    private String companyName;
    private String stockDate;
    private String buy;
    private String sell;
    private String XofPercent;
    private String lowStock;
    private String highStock;



    public StockItem() {
        this.resCompIcon=0;
        this.companyName=null;
        this.stockDate=null;
        this.buy=null;
        this.sell=null;
        this.XofPercent=null;
        this.lowStock=null;
        this.highStock=null;
    }

    public StockItem(int resCompIcon,String companyName) {
        this.resCompIcon=resCompIcon;
        this.companyName=companyName;
        this.stockDate=null;
        this.buy=null;
        this.sell=null;

    }
    public StockItem(int resCompIcon,String companyName,String stockDate,String buy,String sell) {
        this.resCompIcon=resCompIcon;
        this.companyName=companyName;
        this.stockDate=stockDate;
        this.buy=buy;
        this.sell=sell;
    }

    public String getSell() {
        return sell;
    }
    public void setSell(String sell) {
        this.sell = sell;
    }
    public String getBuy() {
        return buy;
    }
    public void setBuy(String buy) {
        this.buy = buy;
    }
    public String getStockDate() {
        return stockDate;
    }
    public void setStockDate(String stockDate) {
        this.stockDate = stockDate;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public int getResCompIcon() {
        return resCompIcon;
    }
    public void setResCompIcon(int resCompIcon) {
        this.resCompIcon = resCompIcon;
    }
    public String getHighStock() {
        return highStock;
    }
    public void setHighStock(String highStock) {
        this.highStock = highStock;
    }
    public String getLowStock() {
        return lowStock;
    }
    public void setLowStock(String lowStock) {
        this.lowStock = lowStock;
    }
}
