package com.panos.sportmonitor.spark.pipelines.sessions.models;

import com.panos.sportmonitor.spark.dto.RawRadarEvent;

public class MatchExtendedDetailsRadarEvent extends MatchEvent {
    private String key;
    private Integer home;
    private Integer away;
    private String hometext;
    private String awaytext;

    public MatchExtendedDetailsRadarEvent(RawRadarEvent raw) {
        super(raw.getData().path("_matchid").asLong(), raw.getDob());

    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getHome() {
        return home;
    }

    public void setHome(Integer home) {
        this.home = home;
    }

    public Integer getAway() {
        return away;
    }

    public void setAway(Integer away) {
        this.away = away;
    }

    public String getHometext() {
        return hometext;
    }

    public void setHometext(String hometext) {
        this.hometext = hometext;
    }

    public String getAwaytext() {
        return awaytext;
    }

    public void setAwaytext(String awaytext) {
        this.awaytext = awaytext;
    }
}
