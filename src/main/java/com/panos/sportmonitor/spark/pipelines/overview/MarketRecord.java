package com.panos.sportmonitor.spark.pipelines.overview;

import com.panos.sportmonitor.dto.Market;

import java.io.Serializable;

class MarketRecord implements Serializable {
    private Long marketId;
    private Long eventId;
    private Long timestamp;
    private String description;
    private String type;
    private Boolean isSuspended;

    MarketRecord(String eventId, Long timestamp, Market market) {
        this.marketId = Long.parseLong(market.getId());
        this.eventId = Long.parseLong(eventId);
        this.timestamp = timestamp;
        this.description = market.getDescription();
        this.type = market.getType();
        this.isSuspended = market.getIsSuspended();
    }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsSuspended() {
        return isSuspended;
    }

    public void setIsSuspended(Boolean suspended) {
        isSuspended = suspended;
    }

    @Override public String toString() {
        return "MarketRecord(eventId = " + eventId + ", marketId = " + marketId + ", type = " + type + ", isSuspended = " + isSuspended + ")";
    }
}
