package com.panos.sportmonitor.spark.pipelines.radar;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

/*
{
    "time": 4,
    "injurytime": 0,
    "safe": 0,
    "safecount": 0,
    "home": {
        "attack": 0,
        "dangerous": 0,
        "safe": 10,
        "attackcount": 0,
        "dangerouscount": 0,
        "safecount": 1
    },
    "away": {
        "attack": 12,
        "dangerous": 34,
        "safe": 4,
        "attackcount": 3,
        "dangerouscount": 1,
        "safecount": 1
    }
}
 */

public class MatchSituationEvent implements Serializable {
    private long id;
    private long matchid;
    private long timestamp;
    private int time;
    private int injurytime;
    private int safe;
    private int safecount;
    private int homeattack;
    private int homedangerous;
    private int homesafe;
    private int homeattackcount;
    private int homedangerouscount;
    private int homesafecount;
    private int awayattack;
    private int awaydangerous;
    private int awaysafe;
    private int awayattackcount;
    private int awaydangerouscount;
    private int awaysafecount;

    public MatchSituationEvent(long matchid, long timestamp, JsonNode node) {
        this.matchid = matchid;
        this.timestamp = timestamp;
        this.time = node.path("time").asInt();
        this.injurytime = node.path("injurytime").asInt();
        this.safe = node.path("safe").asInt();
        this.safecount = node.path("safecount").asInt();

        JsonNode homeNode = node.path("home");
        this.homeattack = homeNode.path("attack").asInt();
        this.homedangerous = homeNode.path("dangerous").asInt();
        this.homesafe = homeNode.path("safe").asInt();
        this.homeattackcount = homeNode.path("attackcount").asInt();
        this.homedangerouscount = homeNode.path("dangerouscount").asInt();
        this.homesafecount = homeNode.path("safecount").asInt();

        JsonNode awayNode = node.path("away");
        this.awayattack = awayNode.path("attack").asInt();
        this.awaydangerous = awayNode.path("dangerous").asInt();
        this.awaysafe = awayNode.path("safe").asInt();
        this.awayattackcount = awayNode.path("attackcount").asInt();
        this.awaydangerouscount = awayNode.path("dangerouscount").asInt();
        this.awaysafecount = awayNode.path("safecount").asInt();

        this.id = Long.parseLong(String.format("1%012d%03d%02d", this.matchid, this.time, this.injurytime));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getMatchid() {
        return matchid;
    }

    public void setMatchid(long matchid) {
        this.matchid = matchid;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getInjurytime() {
        return injurytime;
    }

    public void setInjurytime(int injurytime) {
        this.injurytime = injurytime;
    }

    public int getSafe() {
        return safe;
    }

    public void setSafe(int safe) {
        this.safe = safe;
    }

    public int getSafecount() {
        return safecount;
    }

    public void setSafecount(int safecount) {
        this.safecount = safecount;
    }

    public int getHomeattack() {
        return homeattack;
    }

    public void setHomeattack(int homeattack) {
        this.homeattack = homeattack;
    }

    public int getHomedangerous() {
        return homedangerous;
    }

    public void setHomedangerous(int homedangerous) {
        this.homedangerous = homedangerous;
    }

    public int getHomesafe() {
        return homesafe;
    }

    public void setHomesafe(int homesafe) {
        this.homesafe = homesafe;
    }

    public int getHomeattackcount() {
        return homeattackcount;
    }

    public void setHomeattackcount(int homeattackcount) {
        this.homeattackcount = homeattackcount;
    }

    public int getHomedangerouscount() {
        return homedangerouscount;
    }

    public void setHomedangerouscount(int homedangerouscount) {
        this.homedangerouscount = homedangerouscount;
    }

    public int getHomesafecount() {
        return homesafecount;
    }

    public void setHomesafecount(int homesafecount) {
        this.homesafecount = homesafecount;
    }

    public int getAwayattack() {
        return awayattack;
    }

    public void setAwayattack(int awayattack) {
        this.awayattack = awayattack;
    }

    public int getAwaydangerous() {
        return awaydangerous;
    }

    public void setAwaydangerous(int awaydangerous) {
        this.awaydangerous = awaydangerous;
    }

    public int getAwaysafe() {
        return awaysafe;
    }

    public void setAwaysafe(int awaysafe) {
        this.awaysafe = awaysafe;
    }

    public int getAwayattackcount() {
        return awayattackcount;
    }

    public void setAwayattackcount(int awayattackcount) {
        this.awayattackcount = awayattackcount;
    }

    public int getAwaydangerouscount() {
        return awaydangerouscount;
    }

    public void setAwaydangerouscount(int awaydangerouscount) {
        this.awaydangerouscount = awaydangerouscount;
    }

    public int getAwaysafecount() {
        return awaysafecount;
    }

    public void setAwaysafecount(int awaysafecount) {
        this.awaysafecount = awaysafecount;
    }
}
