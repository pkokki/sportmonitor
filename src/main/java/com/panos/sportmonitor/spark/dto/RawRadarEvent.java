package com.panos.sportmonitor.spark.dto;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

public class RawRadarEvent implements Serializable {
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
}
