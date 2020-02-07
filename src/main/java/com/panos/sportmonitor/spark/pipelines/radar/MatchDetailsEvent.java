package com.panos.sportmonitor.spark.pipelines.radar;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

public class MatchDetailsEvent implements Serializable {
    private final long matchid;
    private final long timestamp;
    private final String key;
    private final Integer home;
    private final Integer away;
    private final String hometext;
    private final String awaytext;

    public MatchDetailsEvent(long matchid, long timestamp, String key, JsonNode value) {
        Integer num;
        this.matchid = matchid;
        this.timestamp = timestamp;
        this.key = key;
        this.hometext = value.path("value").path("home").asText();
        this.awaytext = value.path("value").path("away").asText();
        try {
            num = Integer.parseInt(getHometext());
        } catch (NumberFormatException e) {
            num = null;
        }
        this.home = num;
        try {
            num = Integer.parseInt(getAwaytext());
        } catch (NumberFormatException e) {
            num = null;
        }
        this.away = num;
    }

    public long getMatchid() {
        return matchid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getKey() {
        return key;
    }

    public Integer getHome() {
        return home;
    }

    public Integer getAway() {
        return away;
    }

    public String getHometext() {
        return hometext;
    }

    public String getAwaytext() {
        return awaytext;
    }
}
