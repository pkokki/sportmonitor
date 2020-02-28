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
        Integer num1, num2;
        this.matchId = matchId;
        this.eventStamp = eventStamp;
        this.typeId = typeId;
        this.typeDescription = value.path("name").asText();
        this.homeText = value.path("value").path("home").asText();
        this.awayText = value.path("value").path("away").asText();
        try {
            num1 = Integer.parseInt(this.homeText);
        } catch (NumberFormatException e) {
            num1 = null;
        }
        this.home = num1;
        try {
            num2 = Integer.parseInt(this.awayText);
        } catch (NumberFormatException e) {
            num2 = null;
        }
        this.away = num2;
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
