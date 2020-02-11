package com.panos.sportmonitor.spark.pipelines.sessions.models;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

public class RawRadarEvent implements Serializable {
    private String queryUrl;
    private String event;
    private long dob;
    private int maxage;
    private JsonNode data;

    public RawRadarEvent(String queryUrl, String event, long dob, int maxage, JsonNode data) {
        this.setQueryUrl(queryUrl);
        this.setEvent(event);
        this.setDob(dob);
        this.setMaxage(maxage);
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

    public int getMaxage() {
        return maxage;
    }

    public void setMaxage(int maxage) {
        this.maxage = maxage;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }
}
