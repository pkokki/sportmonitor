package com.panos.sportmonitor.spark.dto;

import java.io.Serializable;

public class EventMasterData implements Serializable {
    private long eventId;
    private long timestamp;
    private String regionId;
    private String regionName;
    private String leagueId;
    private String leagueName;
    private long betRadarId;
    private String title;
    private String startTime;
    private long startTimeTicks;
    private String homeTeam;
    private String awayTeam;

    public EventMasterData(long eventId, long timestamp, String regionId, String regionName, String leagueId,
                           String leagueName, long betRadarId, String title, String startTime, long startTimeTicks,
                           String homeTeam, String awayTeam) {
        this.setEventId(eventId);
        this.setTimestamp(timestamp);
        this.setRegionId(regionId);
        this.setRegionName(regionName);
        this.setLeagueId(leagueId);
        this.setLeagueName(leagueName);
        this.setBetRadarId(betRadarId);
        this.setTitle(title);
        this.setStartTime(startTime);
        this.setStartTimeTicks(startTimeTicks);
        this.setHomeTeam(homeTeam);
        this.setAwayTeam(awayTeam);
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

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public long getBetRadarId() {
        return betRadarId;
    }

    public void setBetRadarId(long betRadarId) {
        this.betRadarId = betRadarId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public long getStartTimeTicks() {
        return startTimeTicks;
    }

    public void setStartTimeTicks(long startTimeTicks) {
        this.startTimeTicks = startTimeTicks;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }
}
