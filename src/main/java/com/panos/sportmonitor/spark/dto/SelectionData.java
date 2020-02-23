package com.panos.sportmonitor.spark.dto;

import java.io.Serializable;

public class SelectionData implements Serializable {
    private long selectionId;
    private long timestamp;
    private boolean active;
    private double prevPrice;
    private double logPrevPrice;
    private double currentPrice;
    private double logCurrentPrice;
    private double priceDiff;

    public SelectionData(long selectionId, long timestamp, boolean active, double currentPrice) {
        this.setSelectionId(selectionId);
        this.setTimestamp(timestamp);
        this.setActive(active);
        this.setCurrentPrice(currentPrice);
    }

    public long getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(long selectionId) {
        this.selectionId = selectionId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getPrevPrice() {
        return prevPrice;
    }

    public double getLogPrevPrice() {
        return logPrevPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getLogCurrentPrice() {
        return logCurrentPrice;
    }

    public double getPriceDiff() {
        return priceDiff;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
        this.logCurrentPrice = Math.log(currentPrice);
        this.calcPriceDiff();
    }

    public void setPrevPrice(double prevPrice) {
        this.prevPrice = prevPrice;
        this.logPrevPrice = Math.log(prevPrice);
        this.calcPriceDiff();
    }

    private void calcPriceDiff() {
        this.priceDiff = (prevPrice > 0 && currentPrice > 0) ? currentPrice - prevPrice : 0;
    }
}
