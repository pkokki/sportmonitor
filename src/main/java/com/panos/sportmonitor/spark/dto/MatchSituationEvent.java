package com.panos.sportmonitor.spark.dto;

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
    private long matchId;
    private long eventStamp;
    private int gameTime;
    private int injuryTime;
    private int safe;
    private int safeCount;
    private int homeAttack;
    private int homeDangerous;
    private int homeSafe;
    private int homeAttackCount;
    private int homeDangerousCount;
    private int homeSafeCount;
    private int awayAttack;
    private int awayDangerous;
    private int awaySafe;
    private int awayAttackCount;
    private int awayDangerousCount;
    private int awaySafeCount;

    public MatchSituationEvent(long matchId, long eventStamp, JsonNode node) {
        this.matchId = matchId;
        this.eventStamp = eventStamp;
        this.gameTime = node.path("time").asInt();
        this.injuryTime = node.path("injurytime").asInt();
        this.safe = node.path("safe").asInt();
        this.safeCount = node.path("safecount").asInt();

        JsonNode homeNode = node.path("home");
        this.homeAttack = homeNode.path("attack").asInt();
        this.homeDangerous = homeNode.path("dangerous").asInt();
        this.homeSafe = homeNode.path("safe").asInt();
        this.homeAttackCount = homeNode.path("attackcount").asInt();
        this.homeDangerousCount = homeNode.path("dangerouscount").asInt();
        this.homeSafeCount = homeNode.path("safecount").asInt();

        JsonNode awayNode = node.path("away");
        this.awayAttack = awayNode.path("attack").asInt();
        this.awayDangerous = awayNode.path("dangerous").asInt();
        this.awaySafe = awayNode.path("safe").asInt();
        this.awayAttackCount = awayNode.path("attackcount").asInt();
        this.awayDangerousCount = awayNode.path("dangerouscount").asInt();
        this.awaySafeCount = awayNode.path("safecount").asInt();

        this.id = Long.parseLong(String.format("1%012d%03d%02d", this.matchId, this.gameTime, this.injuryTime));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEventStamp() {
        return eventStamp;
    }

    public void setEventStamp(long eventStamp) {
        this.eventStamp = eventStamp;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public int getInjuryTime() {
        return injuryTime;
    }

    public void setInjuryTime(int injuryTime) {
        this.injuryTime = injuryTime;
    }

    public int getSafe() {
        return safe;
    }

    public void setSafe(int safe) {
        this.safe = safe;
    }

    public int getSafeCount() {
        return safeCount;
    }

    public void setSafeCount(int safeCount) {
        this.safeCount = safeCount;
    }

    public int getHomeAttack() {
        return homeAttack;
    }

    public void setHomeAttack(int homeAttack) {
        this.homeAttack = homeAttack;
    }

    public int getHomeDangerous() {
        return homeDangerous;
    }

    public void setHomeDangerous(int homeDangerous) {
        this.homeDangerous = homeDangerous;
    }

    public int getHomeSafe() {
        return homeSafe;
    }

    public void setHomeSafe(int homeSafe) {
        this.homeSafe = homeSafe;
    }

    public int getHomeAttackCount() {
        return homeAttackCount;
    }

    public void setHomeAttackCount(int homeAttackCount) {
        this.homeAttackCount = homeAttackCount;
    }

    public int getHomeDangerousCount() {
        return homeDangerousCount;
    }

    public void setHomeDangerousCount(int homeDangerousCount) {
        this.homeDangerousCount = homeDangerousCount;
    }

    public int getHomeSafeCount() {
        return homeSafeCount;
    }

    public void setHomeSafeCount(int homeSafeCount) {
        this.homeSafeCount = homeSafeCount;
    }

    public int getAwayAttack() {
        return awayAttack;
    }

    public void setAwayAttack(int awayAttack) {
        this.awayAttack = awayAttack;
    }

    public int getAwayDangerous() {
        return awayDangerous;
    }

    public void setAwayDangerous(int awayDangerous) {
        this.awayDangerous = awayDangerous;
    }

    public int getAwaySafe() {
        return awaySafe;
    }

    public void setAwaySafe(int awaySafe) {
        this.awaySafe = awaySafe;
    }

    public int getAwayAttackCount() {
        return awayAttackCount;
    }

    public void setAwayAttackCount(int awayAttackCount) {
        this.awayAttackCount = awayAttackCount;
    }

    public int getAwayDangerousCount() {
        return awayDangerousCount;
    }

    public void setAwayDangerousCount(int awayDangerousCount) {
        this.awayDangerousCount = awayDangerousCount;
    }

    public int getAwaySafeCount() {
        return awaySafeCount;
    }

    public void setAwaySafeCount(int awaySafeCount) {
        this.awaySafeCount = awaySafeCount;
    }
}
