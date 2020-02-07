package com.panos.sportmonitor.spark.pipelines.radar;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

class RadarJsonEvent implements Serializable {
    private String queryUrl;
    public String event;
    public long dob;
    private int maxage;
    public JsonNode data;

    RadarJsonEvent(String queryUrl, String event, long dob, int maxage, JsonNode data) {
        this.queryUrl = queryUrl;
        this.event = event;
        this.dob = dob;
        this.maxage = maxage;
        this.data = data;
    }
}
