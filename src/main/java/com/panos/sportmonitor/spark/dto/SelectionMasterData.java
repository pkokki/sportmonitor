package com.panos.sportmonitor.spark.dto;

import java.io.Serializable;

public class SelectionMasterData implements Serializable {
    private long selectionId;
    private long sessionStamp;
    private String description;
    private long marketId;

    public SelectionMasterData(long selectionId, long sessionStamp, String description, long marketId) {
        this.selectionId = selectionId;
        this.setSessionStamp(sessionStamp);
        this.description = description;
        this.marketId = marketId;
    }

    public long getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(long selectionId) {
        this.selectionId = selectionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
