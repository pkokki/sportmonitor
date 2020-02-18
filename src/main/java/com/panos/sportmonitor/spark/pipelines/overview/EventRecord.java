package com.panos.sportmonitor.spark.pipelines.overview;

import com.panos.sportmonitor.common.Event;
import com.panos.sportmonitor.spark.pipelines.sessions.models.RawOverviewEvent;

import java.io.Serializable;

public class EventRecord implements Serializable {
    private Long eventId;
    private Long timestamp;
    private String clockTime;
    private Boolean isSuspended;
    private String homeScore;
    private Integer homeRedCards;
    private String awayScore;
    private Integer awayRedCards;

    public EventRecord(Event e) {
        this.eventId = Long.parseLong(e.getId());
        this.timestamp = e.getTimestamp();
        this.clockTime = e.getClockTime();
        this.isSuspended = e.getIsSuspended();
        this.homeScore = e.getHomeScore();
        this.homeRedCards = e.getHomeRedCards();
        this.awayScore = e.getAwayScore();
        this.awayRedCards = e.getAwayRedCards();
    }

    public EventRecord(RawOverviewEvent e) {
        this.eventId = Long.parseLong(e.getId());
        this.timestamp = e.getTimestamp();
        this.clockTime = e.getClockTime();
        this.isSuspended = e.getIsSuspended();
        this.homeScore = e.getHomeScore();
        this.homeRedCards = e.getHomeRedCards();
        this.awayScore = e.getAwayScore();
        this.awayRedCards = e.getAwayRedCards();
    }

    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

    public Long getEventId() {
        return eventId;
    }
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Boolean getIsSuspended() {
        return isSuspended;
    }

    public void setIsSuspended(Boolean suspended) {
        isSuspended = suspended;
    }

    public String getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(String homeScore) {
        this.homeScore = homeScore;
    }

    public int getHomeRedCards() {
        return homeRedCards;
    }

    public void setHomeRedCards(int homeRedCards) {
        this.homeRedCards = homeRedCards;
    }

    public int getAwayRedCards() {
        return awayRedCards;
    }

    public void setAwayRedCards(int awayRedCards) {
        this.awayRedCards = awayRedCards;
    }

    public String getClockTime() {
        return clockTime;
    }

    public void setClockTime(String clockTime) {
        this.clockTime = clockTime;
    }

    public String getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(String awayScore) {
        this.awayScore = awayScore;
    }

    @Override public String toString() {
        return "EventRecord(id = " + eventId + " " + clockTime +  " " + homeScore + "-" + awayScore + ")";
    }
}
