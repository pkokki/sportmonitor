package com.panos.sportmonitor.spark.dto;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

public class MatchTimelineEvent implements Serializable {
    private long id;
    private long matchId;
    private long eventStamp;
    private int gameMinute;
    private long gameSeconds;
    private long typeId;
    private String typeDescription;
    private String team;

    public MatchTimelineEvent(JsonNode source) {
        this.setId(source.path("_id").asLong());
        this.setMatchId(source.path("matchid").asLong());
        this.setEventStamp(source.path("uts").asLong());
        this.setGameSeconds(source.path("seconds").asLong());
        this.setGameMinute(source.path("time").asInt());
        this.setTypeId(source.path("_typeid").asLong());
        this.setTypeDescription(source.path("type").asText());
        String team = source.path("team").asText();
        this.setTeam("home".equals(team) ? "H" : ("away".equals(team) ? "A" : null));
    }

    @Override public String toString() {
        return "MatchTimelineEvent(id = " + getId()
                + ", matchId = " + getMatchId()
                + ", typeId = " + getTypeId()
                + ")";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public long getEventStamp() {
        return eventStamp;
    }

    public void setEventStamp(long eventStamp) {
        this.eventStamp = eventStamp;
    }

    public int getGameMinute() {
        return gameMinute;
    }

    public void setGameMinute(int gameMinute) {
        this.gameMinute = gameMinute;
    }

    public long getGameSeconds() {
        return gameSeconds;
    }

    public void setGameSeconds(long gameSeconds) {
        this.gameSeconds = gameSeconds;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }
}
