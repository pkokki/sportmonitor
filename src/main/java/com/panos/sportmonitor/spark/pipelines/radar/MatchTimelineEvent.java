package com.panos.sportmonitor.spark.pipelines.radar;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

class MatchTimelineEvent implements Serializable {
    private long id;
    private long matchid;
    private long uts;
    private int time;
    private long seconds;
    private String typeid;
    private String type;
    private String team;

    MatchTimelineEvent(JsonNode source) {
        this.setId(source.path("_id").asLong());
        this.setMatchid(source.path("matchid").asLong());
        this.setUts(source.path("uts").asLong());
        this.setSeconds(source.path("seconds").asLong());
        this.setTime(source.path("time").asInt());
        this.setTypeid(source.path("_typeid").asText());
        this.setType(source.path("type").asText());
        String team = source.path("team").asText();
        this.setTeam("home".equals(team) ? "H" : ("away".equals(team) ? "A" : null));
    }

    @Override public String toString() {
        return "MatchTimelineEvent(id = " + getId()
                + ", matchid = " + getMatchid()
                + ", typeid = " + getTypeid()
                + ")";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMatchid() {
        return matchid;
    }

    public void setMatchid(long matchid) {
        this.matchid = matchid;
    }

    public long getUts() {
        return uts;
    }

    public void setUts(long uts) {
        this.uts = uts;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
