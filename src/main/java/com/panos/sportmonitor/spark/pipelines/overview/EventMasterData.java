package com.panos.sportmonitor.spark.pipelines.overview;

import com.panos.sportmonitor.common.Event;

import java.io.Serializable;

public class EventMasterData implements Serializable {
    private Long eventId;
    private String regionId;
    private String regionName;
    private String leagueId;
    private String leagueName;
    private Long betRadarId;
    private String betRadarLink;
    private String shortTitle;
    private String title;
    private String startTime;
    private Long startTimeTicks;
    private String liveEventLink;
    private String homeTeam;
    private String awayTeam;

    public EventMasterData(Event e) {
        this.eventId = e.getEventId();
        this.regionId = e.getRegionId();
        this.regionName = e.getRegionName();
        this.leagueId = e.getLeagueId();
        this.leagueName = e.getLeagueName();
        this.betRadarId = e.getBetRadarId();
        this.betRadarLink = e.getBetRadarLink();
        this.shortTitle = e.getShortTitle();
        this.title = e.getTitle();
        this.startTime = e.getStartTime();
        this.startTimeTicks = e.getStartTimeTicks();
        this.liveEventLink = e.getLiveEventLink();
        this.homeTeam = e.getHomeTeam();
        this.awayTeam = e.getAwayTeam();
    }


    public Long getEventId() {
        return eventId;
    }
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getRegionId() {
        return regionId;
    }
    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() { return regionName; }
    public void setRegionName(String regionName) { this.regionName = regionName; }

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

    public Long getBetRadarId() {
        return betRadarId;
    }

    public void setBetRadarId(Long betRadarId) {
        this.betRadarId = betRadarId;
    }

    public String getBetRadarLink() {
        return betRadarLink;
    }

    public void setBetRadarLink(String betRadarLink) {
        this.betRadarLink = betRadarLink;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
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

    public Long getStartTimeTicks() {
        return startTimeTicks;
    }

    public void setStartTimeTicks(Long startTimeTicks) {
        this.startTimeTicks = startTimeTicks;
    }

    public String getLiveEventLink() {
        return liveEventLink;
    }

    public void setLiveEventLink(String liveEventLink) {
        this.liveEventLink = liveEventLink;
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


    @Override public String toString() {
        return "Event(id = " + eventId + " " + shortTitle + ")";
    }
}
