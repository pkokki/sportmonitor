package com.panos.sportmonitor.spark.dto;

import java.io.Serializable;

public class EventData implements Serializable {
    private long eventId;
    private long timestamp;
    private String clockTime;
    private boolean suspended;
    private int homeScore;
    private int homeRedCards;
    private int awayScore;
    private int awayRedCards;

    public EventData(long eventId, long timestamp, String clockTime, boolean suspended, int homeScore, int homeRedCards, int awayScore, int awayRedCards) {
        this.setEventId(eventId);
        this.setTimestamp(timestamp);
        this.setClockTime(clockTime);
        this.setSuspended(suspended);
        this.setHomeScore(homeScore);
        this.setHomeRedCards(homeRedCards);
        this.setAwayScore(awayScore);
        this.setAwayRedCards(awayRedCards);
    }


    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getClockTime() {
        return clockTime;
    }

    public void setClockTime(String clockTime) {
        this.clockTime = clockTime;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getHomeRedCards() {
        return homeRedCards;
    }

    public void setHomeRedCards(int homeRedCards) {
        this.homeRedCards = homeRedCards;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public int getAwayRedCards() {
        return awayRedCards;
    }

    public void setAwayRedCards(int awayRedCards) {
        this.awayRedCards = awayRedCards;
    }
}
