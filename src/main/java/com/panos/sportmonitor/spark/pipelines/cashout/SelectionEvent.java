package com.panos.sportmonitor.spark.pipelines.cashout;

import org.apache.commons.math3.util.Precision;

import java.io.Serializable;

public class SelectionEvent implements Serializable {
    private long selectionId;
    private long marketId;
    private long eventId;
    private long timestamp;
    private float currentPrice;
    private boolean isEventSuspended;
    private boolean isMarketSuspended;
    private float prevPrice;
    private float priceDiff;

    public SelectionEvent() {
    }
    public SelectionEvent(String eventId, Long timestamp, boolean isEventSuspended, String marketId, boolean isMarketSuspended, String selectionId, float currentPrice) {
        this.setSelectionId(Long.parseLong(selectionId));
        this.setMarketId(Long.parseLong(marketId));
        this.setEventId(Long.parseLong(eventId));
        this.setTimestamp(timestamp);
        this.setCurrentPrice(currentPrice);
        this.setIsEventSuspended(isEventSuspended);
        this.setIsMarketSuspended(isMarketSuspended);
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
    public float getCurrentPrice() {
        return currentPrice;
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

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public boolean getIsEventSuspended() {
        return isEventSuspended;
    }

    public void setIsEventSuspended(boolean eventSuspended) {
        isEventSuspended = eventSuspended;
    }

    public boolean getIsMarketSuspended() {
        return isMarketSuspended;
    }

    public void setIsMarketSuspended(boolean marketSuspended) {
        isMarketSuspended = marketSuspended;
    }

    public float getPrevPrice() {
        return prevPrice;
    }
    public void setPrevPrice(float prevPrice) {
        if (prevPrice != 0) {
            this.prevPrice = prevPrice;
            this.priceDiff = Precision.round(this.currentPrice - prevPrice, 2);
        }
    }
    public float getPriceDiff() {
        return priceDiff;
    }
}
