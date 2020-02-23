package com.panos.sportmonitor.spark.dto;

import java.io.Serializable;

public class MarketMasterData implements Serializable {
    private long marketId;
    private long timestamp;
    private long eventId;
    private String description;
    private String type;
    private double handicap;

    public MarketMasterData(long marketId, long timestamp, long eventId, String description, String type, double handicap) {
        this.setMarketId(marketId);
        this.setTimestamp(timestamp);
        this.setEventId(eventId);
        this.setDescription(description);
        this.setType(type);
        this.setHandicap(handicap);
    }


    public long getMarketId() {
        return marketId;
    }

    public void setMarketId(long marketId) {
        this.marketId = marketId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

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

    public double getHandicap() {
        return handicap;
    }

    public void setHandicap(double handicap) {
        this.handicap = handicap;
    }
}
