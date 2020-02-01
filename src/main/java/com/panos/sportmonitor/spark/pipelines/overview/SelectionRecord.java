package com.panos.sportmonitor.spark.pipelines.overview;

import com.panos.sportmonitor.dto.Selection;

import java.io.Serializable;

class SelectionRecord implements Serializable {
    private Long selectionId;
    private Long marketId;
    private Long eventId;
    private Long timestamp;
    private String description;
    private Float price;

    SelectionRecord(String eventId, Long timestamp, String marketId, Selection s) {
        this.selectionId = Long.parseLong(s.getId());
        this.marketId = Long.parseLong(marketId);
        this.eventId = Long.parseLong(eventId);
        this.timestamp = timestamp;
        this.description = s.getDescription();
        this.price = s.getPrice();
    }

    public Long getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(Long selectionId) {
        this.selectionId = selectionId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override public String toString() {
        return "SelectionRecord(eventId = " + eventId + ", marketId = " + marketId + ", selectionId = " + selectionId + ", price = " + price + ")";
    }
}
