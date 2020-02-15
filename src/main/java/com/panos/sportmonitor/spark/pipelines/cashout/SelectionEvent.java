package com.panos.sportmonitor.spark.pipelines.cashout;

import java.io.Serializable;

public class SelectionEvent implements Serializable {
    private long selectionId;
    private long marketId;
    private long eventId;
    private long timestamp;
    private float price;
    private boolean isEventSuspended;
    private boolean isMarketSuspended;

    public SelectionEvent() {
    }
    public SelectionEvent(String eventId, Long timestamp, boolean isEventSuspended, String marketId, boolean isMarketSuspended, String selectionId, float price) {
        this.setSelectionId(Long.parseLong(selectionId));
        this.setMarketId(Long.parseLong(marketId));
        this.setEventId(Long.parseLong(eventId));
        this.setTimestamp(timestamp);
        this.setPrice(price);
        this.setEventSuspended(isEventSuspended);
        this.setMarketSuspended(isMarketSuspended);
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
    public float getPrice() {
        return price;
    }
    public boolean getIsEventSuspended() {
        return isEventSuspended();
    }
    public boolean getIsMarketSuspended() {
        return isMarketSuspended();
    }

    @Override public String toString() {
        return "SelectionEvent(eventId = " + getEventId() + ", marketId = " + getMarketId() + ", selectionId = " + getSelectionId() + ", price = " + getPrice() + ")";
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

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isEventSuspended() {
        return isEventSuspended;
    }

    public void setEventSuspended(boolean eventSuspended) {
        isEventSuspended = eventSuspended;
    }

    public boolean isMarketSuspended() {
        return isMarketSuspended;
    }

    public void setMarketSuspended(boolean marketSuspended) {
        isMarketSuspended = marketSuspended;
    }
}
