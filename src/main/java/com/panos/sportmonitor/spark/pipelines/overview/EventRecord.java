package com.panos.sportmonitor.spark.pipelines.overview;

import com.panos.sportmonitor.dto.Event;

import java.io.Serializable;

class EventRecord implements Serializable {
    private Long eventId;
    private Long timestamp;
    private String regionId;
    private String regionName;
    private String leagueId;
    private String leagueName;
    private Long betRadarId;
    private String betRadarLink;
    private String clockTime;
    private String shortTitle;
    private String title;
    private String startTime;
    private Long startTimeTicks;
    private Boolean isSuspended;
    private String liveEventLink;
    private String homeTeam;
    private String homeScore;
    private Integer homeRedCards;
    private String awayTeam;
    private String awayScore;
    private Integer awayRedCards;

    EventRecord(Event e) {
        this.eventId = Long.parseLong(e.getId());
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

    public Boolean getIsSuspended() {
        return isSuspended;
    }

    public void setIsSuspended(Boolean suspended) {
        isSuspended = suspended;
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

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
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
        return "EventRecord(id = " + eventId + " " + clockTime + " " + shortTitle + " " + homeScore + "-" + awayScore + ")";
    }
}
