package com.panos.sportmonitor.spark.dto;

import java.io.Serializable;

public class EventScoreChange implements Serializable {
    private long eventId;
    private long timestamp;
    private String clockTime;
    private int home;
    private int away;
    private int homeDiff;
    private int awayDiff;

    public EventScoreChange(long eventId, long timestamp, String clockTime, int home, int away, int homeDiff, int awayDiff) {
        this.eventId = eventId;
        this.timestamp = timestamp;
        this.clockTime = clockTime;
        this.home = home;
        this.away = away;
        this.homeDiff = homeDiff;
        this.awayDiff = awayDiff;
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

    public int getHome() {
        return home;
    }

    public void setHome(int home) {
        this.home = home;
    }

    public int getAway() {
        return away;
    }

    public void setAway(int away) {
        this.away = away;
    }

    public int getHomeDiff() {
        return homeDiff;
    }

    public void setHomeDiff(int homeDiff) {
        this.homeDiff = homeDiff;
    }

    public int getAwayDiff() {
        return awayDiff;
    }

    public void setAwayDiff(int awayDiff) {
        this.awayDiff = awayDiff;
    }
}
