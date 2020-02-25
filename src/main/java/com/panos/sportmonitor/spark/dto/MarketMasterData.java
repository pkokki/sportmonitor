package com.panos.sportmonitor.spark.dto;

import java.io.Serializable;

public class MarketMasterData implements Serializable {
    private long marketId;
    private long sessionStamp;
    private long eventId;
    private String description;
    private String type;
    private double handicap;

    public MarketMasterData(long marketId, long sessionStamp, long eventId, String description, String type, double handicap) {
        this.setMarketId(marketId);
        this.setSessionStamp(sessionStamp);
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

    public long getSessionStamp() {
        return sessionStamp;
    }

    public void setSessionStamp(long sessionStamp) {
        this.sessionStamp = sessionStamp;
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
