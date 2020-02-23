package com.panos.sportmonitor.spark.dto;

import java.io.Serializable;

public class SelectionMasterData implements Serializable {
    private long selectionId;
    private long timestamp;
    private String description;
    private long marketId;

    public SelectionMasterData(long selectionId, long timestamp, String description, long marketId) {
        this.selectionId = selectionId;
        this.setTimestamp(timestamp);
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
