package com.panos.sportmonitor.spark.dto;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

public class RawRadarEvent implements Serializable {
    public static final String MATCH_SITUATION = "stats_match_situation";
    public static final String MATCH_TIMELINE = "match_timeline";
    public static final String MATCH_DETAILS = "match_detailsextended";

    private String queryUrl;
    private String event;
    private long dob;
    private int maxAge;
    private JsonNode data;

    public RawRadarEvent(String queryUrl, String event, long dob, int maxAge, JsonNode data) {
        this.setQueryUrl(queryUrl);
        this.setEvent(event);
        this.setDob(dob);
        this.setMaxAge(maxAge);
        this.setData(data);
    }

    public String getQueryUrl() {
        return queryUrl;
    }

    public void setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public long getDob() {
        return dob;
    }

    public void setDob(long dob) {
        this.dob = dob;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    public long getMatchId() {
        switch (getEvent()) {
            case RawRadarEvent.MATCH_SITUATION:
                return Long.parseLong(getData().path("matchid").asText());
            case RawRadarEvent.MATCH_DETAILS:
                return getData().path("_matchid").asLong();
            case RawRadarEvent.MATCH_TIMELINE:
                return getData().path("matchid").asLong();
        }
        return -1;
    }

    public boolean canParse() {
        String type = getEvent();
        return type.equals(RawRadarEvent.MATCH_SITUATION)
                || type.equals(RawRadarEvent.MATCH_DETAILS)
                || type.equals(RawRadarEvent.MATCH_TIMELINE);
    }
}
