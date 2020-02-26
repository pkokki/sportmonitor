package com.panos.sportmonitor.spark.dto;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

public class MatchDetailsEvent implements Serializable {
    private final long matchId;
    private final long eventStamp;
    private final String typeId;
    private final String typeDescription;
    private final Integer home;
    private final Integer away;
    private final String homeText;
    private final String awayText;

    public MatchDetailsEvent(long matchId, long eventStamp, String typeId, JsonNode value) {
        Integer num;
        this.matchId = matchId;
        this.eventStamp = eventStamp;
        this.typeId = typeId;
        this.typeDescription = value.path("name").asText();
        this.homeText = value.path("value").path("home").asText();
        this.awayText = value.path("value").path("away").asText();
        try {
            num = Integer.parseInt(getHomeText());
        } catch (NumberFormatException e) {
            num = null;
        }
        this.home = num;
        try {
            num = Integer.parseInt(getAwayText());
        } catch (NumberFormatException e) {
            num = null;
        }
        this.away = num;
    }

    public long getMatchId() {
        return matchId;
    }

    public long getEventStamp() {
        return eventStamp;
    }

    public String getTypeId() {
        return typeId;
    }

    public Integer getHome() {
        return home;
    }

    public Integer getAway() {
        return away;
    }

    public String getHomeText() {
        return homeText;
    }

    public String getAwayText() {
        return awayText;
    }

    public String getTypeDescription() {
        return typeDescription;
    }
}
