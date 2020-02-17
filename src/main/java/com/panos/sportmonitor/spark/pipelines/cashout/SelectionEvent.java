package com.panos.sportmonitor.spark.pipelines.cashout;

import org.apache.commons.math3.util.Precision;

import java.io.Serializable;

public class SelectionEvent implements Serializable {
    private long selectionId;
    private long eventId;
    private long marketId;
    private long timestamp;
    private boolean isActive;
    private double prevPrice;
    private double logPrevPrice;
    private double currentPrice;
    private double logCurrentPrice;
    private double priceDiff;

    public SelectionEvent() {
    }

    public SelectionEvent(String eventId, Long timestamp, boolean isEventSuspended, String marketId, boolean isMarketSuspended, String selectionId, double currentPrice, double prevPrice) {
        this(eventId, timestamp, isEventSuspended, marketId, isMarketSuspended, selectionId, currentPrice);
        this.setPrevPrice(prevPrice);
    }

    public SelectionEvent(String eventId, Long timestamp, boolean isEventSuspended, String marketId, boolean isMarketSuspended, String selectionId, double currentPrice) {
        this.setSelectionId(Long.parseLong(selectionId));
        this.setMarketId(Long.parseLong(marketId));
        this.setEventId(Long.parseLong(eventId));
        this.setTimestamp(timestamp);
        this.setCurrentPrice(currentPrice);
        isActive = !isEventSuspended && !isMarketSuspended;
    }

    public long getSelectionId() {
        return selectionId;
    }
    public long getEventId() {
        return eventId;
    }
    public long getTimestamp() { return timestamp; }
    public long getMarketId() {
        return marketId;
    }
    public double getCurrentPrice() {
        return currentPrice;
    }
    public double getLogCurrentPrice() {
        return logCurrentPrice;
    }

    @Override public String toString() {
        return "SelectionEvent(eventId = " + getEventId() + ", marketId = " + getMarketId() + ", selectionId = " + getSelectionId() + ", price = " + getCurrentPrice() + ")";
    }

    public void setSelectionId(long selectionId) {
        this.selectionId = selectionId;
    }

    public void setMarketId(long marketId) {
        this.marketId = marketId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = Precision.round(currentPrice, 2);
        this.logCurrentPrice = Math.log(currentPrice);
        if (prevPrice != 0)
            this.priceDiff = Precision.round(this.currentPrice - prevPrice, 2);
    }

    public boolean getIsActive() {
        return isActive;
    }

    public double getPrevPrice() {
        return prevPrice;
    }
    public void setPrevPrice(double prevPrice) {
        if (prevPrice != 0) {
            this.prevPrice = Precision.round(prevPrice, 2);
            this.priceDiff = Precision.round(this.currentPrice - prevPrice, 2);
            this.logPrevPrice = Math.log(prevPrice);
        }
    }
    public double getLogPrevPrice() {
        return logPrevPrice;
    }
    public double getPriceDiff() {
        return priceDiff;
    }
}
