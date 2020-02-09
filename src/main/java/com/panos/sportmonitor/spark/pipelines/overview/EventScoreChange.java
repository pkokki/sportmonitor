package com.panos.sportmonitor.spark.pipelines.overview;

import java.io.Serializable;

public class EventScoreChange implements Serializable {
    private long eventid;
    private long timestamp;
    private String clocktime;

    private int home;
    private int away;

    private int homediff;
    private int awaydiff;

    public EventScoreChange() {
    }

    public long getEventid() {
        return eventid;
    }

    public void setEventid(long eventid) {
        this.eventid = eventid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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

    public int getHomediff() {
        return homediff;
    }

    public void setHomediff(int homediff) {
        this.homediff = homediff;
    }

    public int getAwaydiff() {
        return awaydiff;
    }

    public void setAwaydiff(int awaydiff) {
        this.awaydiff = awaydiff;
    }

    public String getClocktime() {
        return clocktime;
    }

    public void setClocktime(String clocktime) {
        this.clocktime = clocktime;
    }

    @Override
    public String toString() {
        return String.format("EventScoreChange(%d @ %d [%s] %d-%d, %d/%d)", eventid, timestamp, clocktime, home, away, homediff, awaydiff);
    }
}
